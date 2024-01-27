from flask import Flask, request, jsonify
from deepface import DeepFace
from http import HTTPStatus
import base64
from werkzeug.utils import secure_filename
import uuid
from botocore.client import Config
from flask_cors import CORS
import uuid
import modules.table as table_info
import modules.db_connect  as db_util
import modules.s3_connect as s3_util
from sqlalchemy import insert, select
from sqlalchemy.exc import SQLAlchemyError
from io import BytesIO
import numpy as np
import cv2
import requests

## db 설정
album_table = table_info.get_album_table()
file_table = table_info.get_file_table()
kid_table = table_info.get_kid_table()
# feature_table = table_info.get_feature_table()
engine = db_util.get_engine()

# s3 설정
s3 = s3_util.get_s3_connector()
s3_bucket_name = s3_util.s3_bucket_name
db_base_url = s3_util.db_base_url

app = Flask(__name__)


CORS(app, origins="*")
@app.route('/')
def index():
    return "home"

@app.route("/test/verify", methods=["POST"])
def verify_test():
    
    image1 = request.files.get("image1")
    image2 = request.files.get("image2")
    
    np1 = np.frombuffer(image1.read(), dtype=np.uint8)
    np2 = np.frombuffer(image2.read(), dtype=np.uint8)

    decoded1 = cv2.imdecode(np1, cv2.IMREAD_COLOR)
    decoded2 = cv2.imdecode(np2, cv2.IMREAD_COLOR)

    result = verify(decoded1, decoded2)
    
    return jsonify({"data" : result})
     

def get_classroom_npArray(classroomId) :
    # 반이 아이들을 모두 가져오자
    base_url = ""
    with engine.connect() as conn : 
        result = conn.execute(
            select(kid_table).where(kid_table.c.classroom_id == classroomId)
        )

        kid_dict = dict()
        for r in result.mappings() :
            kid_id = r["kid_id"]
            url = r["kid_profile"] 
            kid_dict[kid_id] = url

            url_parser = url.split("/")
            url = base_url + url_parser[-1]
            response = s3.get_object(Bucket = s3_bucket_name, Key= url)
            data = response["Body"].read()
            encoded_img = np.frombuffer(data, dtype = np.uint8)
            img = cv2.imdecode(encoded_img,cv2.IMREAD_COLOR)
            
            kid_dict[kid_id] = img

    return kid_dict


@app.route("/s3/upload/<classroomId>", methods=["POST"])
def uploadS3(classroomId) :
    if(request.method == "POST") :
        files = request.files.getlist("images")
        size = len(files)
        with engine.connect() as conn : 
            result = conn.execute(
                insert(album_table),
                {
                    "album_date" : "2024-01-27",
                    "classroom_id" : classroomId,
                    "album_title" : "1월 27일 엘범 테스트"
                }
            )
            album_id = result.inserted_primary_key[0]
            photo = []
            for file in files :
                file_name , file_extension = secure_filename(file.filename).split(".")

                file_uuid = str(uuid.uuid4())
                path = "test/" + file_uuid + "." + file_extension
                s3.upload_fileobj(file.stream, s3_bucket_name, path)
                photo.append({
                    "album_id" : album_id,
                    "file_name" : file_name,
                    "file_path" : db_base_url + path,
                })
            conn.execute(
                insert(file_table),
                photo
            ) 

            conn.commit()
            # db 저장 url 
            return jsonify({"status" : 200 , "message" : "upload success", "data" : {"size" : size}} )
    
    return jsonify({"status" : 500 , "message" : "internal server error"})

models = [
  "VGG-Face", 
  "Facenet", 
  "Facenet512", 
  "OpenFace", 
  "DeepFace", 
  "DeepID", 
  "ArcFace", 
  "Dlib", 
  "SFace",
]

metrics = ["cosine", "euclidean", "euclidean_l2"]

def verify(image1, image2):
        '''
        params
        image1 : np array
        image2 : np array
        '''
        result = DeepFace.verify(image1, image2, 
                                 model_name="VGG-Face",
                                 normalization="base", 
                                 align=True, 
                                 detector_backend="opencv",
                                 enforce_detection=False,
                                 distance_metric="euclidean_l2"
                                 )
            # 결과 반환
        
        return result

if __name__ == '__main__':
    app.run(debug=True)

