from flask import Flask, jsonify, request, redirect, abort
import face_recognition
from http import HTTPStatus
import cv2
import numpy as np
import os
from werkzeug.utils import secure_filename
import uuid
from decouple import config
import boto3
from botocore.client import Config
from flask_sqlahcmey import SQLAlchemy

# s3 설정
access_key = config("access_key")
secret_key = config("secret_key")
bucket_name = config("bucket_name")

app = Flask(__name__)

s3 = boto3.client(
    's3',
    aws_access_key_id=access_key,
    aws_secret_access_key=secret_key,
    config=Config(signature_version='s3v4')
)


@app.route("/health", methods=["GET"])
def health() :
    return jsonify({"status" : HTTPStatus.OK, "success" : True})
    

    
### s3 업로드 테스트 ###
@app.route("/s3/upload", methods=["POST"])
def uploadS3() :
    if(request.method == "POST") :
        files = request.files.getlist("images")

        for file in files :
            folder = "class/"
            file_name = secure_filename(file.filename)


            path = os.path.join(folder,str(uuid.uuid1()),os.path.splitext(file_name)[1])
            s3.upload_fileobj(file, bucket_name, path)
        return jsonify({"status" : HTTPStatus.OK , "message" : "upload success", "data" : {"size" : len(files)}} )

    return jsonify({"status" : HTTPStatus.INTERNAL_SERVER_ERROR , "message" : "internal server error"})

### 아이 특징 등록 ###
@app.route("/api/v2/regist", methods=["POST"])
def registKid() :

    # 사진하나를 받는다.
    if (request.method == 'POST') :
        image = request.files["image"]
        
        encoding = face_recognition.face_encodings(image)
 
        # db 저장
    return "data"


### 사진을 인식하여 아이 각각의 앨범 등록 ###
@app.route("/api/v2/album", methods=['POST'])
def createAlbum():
    if(request.method == 'POST') :
        # 이미지 리스트를 받는다.
        images = request.files.getlist("images")
        saveImage = [] 
        children = [] 

        # 반 앨범에 저장하기 (s3 + db)
        # s3 에 저장하기



        # children list 불러오기 

        for image in images :
            # 반 엘범에 저장.
            file_byte = np.fromfile(image,np.uint8)
            decoded_image = cv2.imdecode(file_byte, cv2.IMREAD_COLOR)
            encodings = face_recognition.face_encodings(decoded_image)
            
            for encoding in encodings : 
                
                for child in children : 
                    if(face_recognition.compare_faces([child], encoding)) :
                        # 아이폴더에 저장
                        save = True
    
            
            #  detection version
            #  
            # for location in locations :
            #     top, right, bottom, left = location
            #     face_image = decoded_image[top:bottom,left:right]

            #     # 알고 있는 사진과 일치 하는가?
            #     # children feature >> db로 저장할 건지? >> 속도 느려지면 db로 저장하자.

            #     #  encoded_image = face_recognition.face_encodings
                
            #     # for child in children :
            #     #     if(face_recognition.compare_faces([child], encoded_image)):
            #     #         # 아이 폴더에 image를 저장한다. 
            #     #         save = True
          
        

        return jsonify({"data" : "success", "status": HTTPStatus.OK})
    else : 
        return jsonify({"status" : HTTPStatus.INTERNAL_SERVER_ERROR , "data" : "false"})
if __name__ == "__main__":
    app.run(debug=True)