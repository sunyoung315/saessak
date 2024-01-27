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
                path = "/test" + file_uuid + "." + file_extension
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

@app.route('/verify', methods=['POST'])
def verify():
    try:
        # POST 요청에서 이미지 데이터를 가져옴
        image1_data = request.files['image1'].read()
        image2_data = request.files['image2'].read()
    

        # 이미지 데이터를 base64 문자열로 변환
        image1_base64 = "data:image/png;base64," + base64.b64encode(image1_data).decode('utf-8')
        image2_base64 = "data:image/png;base64," + base64.b64encode(image2_data).decode('utf-8')
        # 이미지 간의 얼굴 유사성 계산
        result = DeepFace.verify(image1_base64, image2_base64)
            # 결과 반환
        return jsonify(result)
    except Exception as e:
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    app.run(debug=True)

