from flask import Flask, jsonify, request
import face_recognition
from http import HTTPStatus
import cv2
import numpy as np
import os
from werkzeug.utils import secure_filename
import uuid
import modules.table as table_info
import modules.db_connect  as db_util
import modules.s3_connect as s3_util
from sqlalchemy import insert, select
from sqlalchemy.exc import SQLAlchemyError


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

def get_face_feature(s3_url):
    if not s3_url : return [0]*128

    response = s3.get_object(Bucket = s3_bucket_name, Key= s3_url)
    data = response["Body"].read()
    encoded_img = np.frombuffer(data, dtype = np.uint8)
    img = cv2.imdecode(encoded_img,cv2.IMREAD_COLOR)
    encodeding = face_recognition.face_encodings(img)[0]
    
    return encodeding


@app.route("/health", methods=["GET"])
def health() :
    return jsonify({"status" : 200, "success" : True})

## get을 통해 image 파일 추출
@app.route("/s3/download", methods=["GET"])
def image_download() :
    response = s3.get_object(Bucket = s3_bucket_name, Key= "class/1d6d0f50-c4c4-4f08-916a-d8c0d0730094.png")
    data = response["Body"].read()
    encoded_img = np.frombuffer(data, dtype = np.uint8)
    img = cv2.imdecode(encoded_img,cv2.IMREAD_COLOR)
    
    encoded = face_recognition.face_encodings(img)
    return jsonify({"status" : HTTPStatus.OK})

# s3 업로드 테스트
@app.route("/s3/upload/<classroomId>", methods=["POST"])
def uploadS3(classroomId) :
    if(request.method == "POST") :
        files = request.files.getlist("images")
        size = len(files)
        with engine.connect() as conn : 
            result = conn.execute(
                insert(album_table),
                {
                    "album_date" : "2024-01-17",
                    "classroom_id" : classroomId,
                    "album_title" : "업로드 타이틀"
                }
            )
            album_id = result.inserted_primary_key[0]
            photo = []
            for file in files :
                file_name , file_extension = secure_filename(file.filename).split(".")

                file_uuid = str(uuid.uuid4())
                path = file_uuid + "." + file_extension
                s3.upload_fileobj(file, s3_bucket_name, path)

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

@app.route("/api/v2/regist", methods=["POST"])
def registKid() :

    # 사진하나를 받는다.
    if (request.method == 'POST') :
        image = request.files["image"]
        
        encoding = face_recognition.face_encodings(image)
 
        # db 저장
    return "data"

@app.route("/api/v2/album/<classroomId>", methods=['POST'])
def create_album(classroomId):
    if(request.method == 'POST') :
        # 이미지 리스트를 받는다.
        images = request.files.getlist("images")
    
        album_date = request.form.get("albumDate")
        album_title = request.form.get("albumTitle")

        # 반 앨범 생성
        try : 

            with engine.connect() as conn:
                # 반에 있는 학생들의 feature 가져오기
                child_dict = dict()
                result = conn.execute(
                    select(kid_table).where(kid_table.c.classroom_id == classroomId)
                )
                for r in result.mappings() :
                    kid_id = r["kid_id"]
                    url = r["kid_profile"]
                    child_dict[kid_id] = get_face_feature(url)
                # 반 엘범 제작
               
                class_album = []
                child_album = dict()
                s3_list = []

                for image in images :
                    file_name , file_extension = secure_filename(image.filename).split(".")

                    file_uuid = str(uuid.uuid4())
                    path = file_uuid+ "." + file_extension

                    # s3 업로드하면 image파일을 못읽는데.. 어떻게 해야.. >> 업로드 시점을 transaction이 끝난뒤로 
                    s3_list.append(path)    
                    #반 엘범에 저장
                    add_object = {
                        "file_name" : file_name,
                        "file_path" : db_base_url + path,
                    }

                    class_album.append(add_object)

                    file_byte = np.fromfile(image,np.uint8)
                    decoded_image = cv2.imdecode(file_byte, cv2.IMREAD_COLOR)
                    encodings = face_recognition.face_encodings(decoded_image)
                    for kid_id , feature in child_dict.items() :
                        for encoding in encodings :
                            if face_recognition.compare_faces([feature],encoding, tolerance=0.6) : 
                                if kid_id in child_album :
                                    child_album[kid_id].append(add_object)
                                else :
                                    child_album[kid_id] = [add_object]

                ## 분류 완료
                result = conn.execute(
                    insert(album_table),
                    {
                        "album_date" : album_date,
                        "classroom_id" : classroomId,
                        "album_title" : album_title
                    }
                )                    
                album_id = result.inserted_primary_key[0]
                
                for col in class_album : 
                    col["album_id"] = album_id
                    
                conn.execute(
                    insert(file_table),
                    class_album
                )
                    
                for key, value in child_album.items() : 
                    result = conn.execute(
                        insert(album_table),
                        {
                            "kid_id" : key, 
                            "album_date" : album_date,
                            "classroom_id" : classroomId,
                            "album_title" : album_title
                        }

                    )
                    album_id = result.inserted_primary_key[0]
                    print("this point: " , album_id)
                    for col in value :
                        col["album_id"] = album_id

                    conn.execute(
                        insert(file_table),
                        value
                    )
                    
                conn.commit()  
                
                for  i in range(len(images)) :
                    s3.upload_fileobj(images[i], s3_bucket_name, s3_list[i])

            
            return jsonify({"data" : "success", "status": HTTPStatus.OK})
        
        
        except SQLAlchemyError as e:
            conn.rollback()
            print(e)    
            
    
    return jsonify({"status" : HTTPStatus.BAD_REQUEST , "data" : "false"})
if __name__ == "__main__":
    app.run(debug=True)