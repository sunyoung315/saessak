from flask import Flask, request, jsonify
from deepface import DeepFace
from http import HTTPStatus
from werkzeug.utils import secure_filename
import uuid
from botocore.client import Config
from flask_cors import CORS
import uuid
import modules.table as table_info
import modules.db_connect  as db_util
import modules.s3_connect as s3_util
from modules.jwt_function import Validator
from sqlalchemy import insert, select
from sqlalchemy.exc import SQLAlchemyError
from io import BytesIO
import numpy as np
import cv2
import os
from deepface.commons import distance as dst 
from decouple import config
## db 설정
album_table = table_info.get_album_table()
file_table = table_info.get_file_table()
user_table = table_info.get_user_table()
kid_table = table_info.get_kid_table()
teacher_table = table_info.get_teacher_table()
engine = db_util.get_engine()

# s3 설정
s3 = s3_util.get_s3_connector()
s3_bucket_name = s3_util.s3_bucket_name
db_base_url = s3_util.db_base_url

app = Flask(__name__)

# jwt 설정
jwt_secret = config("JWT_SECRET_KEY")
token_validator = Validator(jwt_secret)


CORS(app, origins="*")

def get_face_embeddings (img ): 
    represent_objs = DeepFace.represent(
        img_path=img,
        detector_backend="opencv",
        enforce_detection=False,
        model_name="VGG-Face",
        normalization="VGGFace2"
    )
    embedding_list = []
    
    for face in represent_objs :
        embedding_list.append(face["embedding"])

    return embedding_list

@app.route("/ai/auth" , methods=["POST"])
def refactor():
    ## 토큰 유효성 검사
    auth_header = request.headers.get('Authorization')
    if not auth_header : return jsonify({"status" : HTTPStatus.UNAUTHORIZED,"code" : "A105"})
    if auth_header.startswith("Bearer ") :
        # 헤더에 Bearer 토큰이 있을 때
        access_token = auth_header.split(' ')[1]
        result = token_validator.validate_token(access_token)
        data = result["data"]
        classroomId = data["classroom_id"]
        if not result["isValid"] :
            return jsonify(result)
        
    else :
        # 헤더에 Bearer 토큰이 없을 떄
        return jsonify({"status" : HTTPStatus.UNAUTHORIZED , "code": "A104"})

    try : 
        images = request.files.getlist("images")
        album_date = request.form.get("albumDate")
        album_title = request.form.get("albumTitle")
        

        return jsonify({"status" : HTTPStatus.CREATED, "message" : "success"})
    except Exception as e:
        return jsonify({"error" : str(e)})

@app.route("/ai/album" , methods=["POST"])
def verifyAlbum():
    ## 토큰 유효성 검사
    
    auth_header = request.headers.get('Authorization')

    if auth_header and auth_header.startswith("Bearer ") :
        # 헤더에 Bearer 토큰이 있을 때
        access_token = auth_header.split(' ')[1]
        token_info = token_validator.validate_token(access_token)
        
        if not result["isValid"] :
            return jsonify(token_info)
        
    else :
        # 헤더에 Bearer 토큰이 없을 떄
        return jsonify({"status" : HTTPStatus.UNAUTHORIZED , "message": "접근이 불가능 합니다."})

    try : 
        images = request.files.getlist("images")
        album_date = request.form.get("albumDate")
        album_title = request.form.get("albumTitle")
        user_data = token_info["data"]
        classroomId = user_data["classroom_id"]
        with engine.connect() as conn : 
            # 반의 아이들을 모두 가져오자
            result = conn.execute(
                select(user_table)
                .join(kid_table)
                .where(user_table.c.classroom_id == classroomId)
            )
            kid_dict = dict()
            kid_embeddings = dict()
            kid_album = dict()
            for r in result.mappings() :
                kid_id = r["id"]
                url = r["profile"] 
                if url is None : continue
                base_url = "profile/"
                kid_dict[kid_id] = url
                kid_album[kid_id] = []


                url_parser = url.split("/")
                url = base_url + url_parser[-1]
                response = s3.get_object(Bucket = s3_bucket_name, Key= url)
                data = response["Body"].read()
                encoded_img = np.frombuffer(data, dtype = np.uint8)
                img = cv2.imdecode(encoded_img,cv2.IMREAD_COLOR)
                
                embedding = get_face_embeddings(img)
                kid_embeddings[kid_id] = embedding


            class_album = []
            s3_data= []
            # ### 파일들을 순회한다.

            for image in images :   
                
                content_type = image.content_type
                file_name, file_extension = os.path.splitext(image.filename)

                file_uuid = str(uuid.uuid4())
                path = "album/" + file_uuid+ "." + file_extension

                add_object = {
                    "file_name" : file_name,
                    "file_path" : db_base_url + path,
                }
                #반은 일단 저장
                class_album.append(add_object)
            

            # return jsonify({"data" : class_album})
                file_byte = np.frombuffer(image.read(),np.uint8)
                decoded_image = cv2.imdecode(file_byte, cv2.IMREAD_COLOR)
                file_obj = BytesIO(file_byte)
                s3_data.append({
                    "path":path,
                    "content_type": content_type,
                    "file_obj": file_obj
                })
                # #아이 분류
                image_embeddings = get_face_embeddings(decoded_image)


                for kid_id, kid_embedding in kid_embeddings.items() : 
                    
                    for kid_embed in kid_embedding :
                        
                        for image_embed in image_embeddings : 
                            # 유사한 사진이 있는지?
                            # distance_cosine = dst.findCosineDistance(kid_embed, image_embed)
                            # distance_euclidean = dst.findEuclideanDistance(kid_embed, image_embed)
                            distance_euclidean_l2 = dst.findEuclideanDistance(
                                dst.l2_normalize(kid_embed), dst.l2_normalize(image_embed)
                            )

                            # threshold_cosine = dst.findThreshold(model_name="VGG-Face",distance_metric="cosine")
                            # threshold_euclidean = dst.findThreshold(model_name="VGG-Face",distance_metric="euclidean")
                            threshold_euclidean_l2 = dst.findThreshold(model_name="VGG-Face",distance_metric="euclidean_l2")

                            if distance_euclidean_l2 <= threshold_euclidean_l2 :
                                kid_album[kid_id].append(add_object.copy())
                                break
                        

            ## 분류 완료 
            # 반엘범 생성
            created_albumid = []
            result = conn.execute(
                insert(album_table),
                {
                    "album_date" : album_date,
                    "classroom_id" : classroomId,
                    "album_title" : album_title
                }
            )
            album_id = result.inserted_primary_key[0]
            created_albumid.append(album_id)
            for row in class_album : 
                row["album_id"] = album_id
            
            conn.execute(
                insert(file_table),
                class_album
            )
            # 아이앨범 생성
            for key, value in kid_album.items() :
                if len(value) == 0 : continue
                
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
                created_albumid.append(album_id)

                for row in value : 
                    row["album_id"] = album_id
                conn.execute(
                    insert(file_table),
                    value
                )
            
            # for data in s3_data : 
            #     print(data)
            # ## s3업로드
            for data in s3_data :
                s3.upload_fileobj(data["file_obj"], s3_bucket_name, data["path"], ExtraArgs={'ContentType': data["content_type"]}  )
            
            conn.commit()
            return jsonify({"status": HTTPStatus.CREATED,"message":"성공하였습니다." ,"data" : {"albumid" : created_albumid}})
    except SQLAlchemyError as sqlerror :
        conn.rollback()
        print(jsonify({"error" : str(sqlerror)}))
        return jsonify({"status" : HTTPStatus.INTERNAL_SERVER_ERROR, "message" : "Error가 발생했습니다."})
    except Exception as e:
        print(jsonify({"error" : str(e)}))
        return jsonify({"status" : HTTPStatus.INTERNAL_SERVER_ERROR, "message" : "Error가 발생했습니다."})

        


# @app.route("/ai/album/<classroomId>", methods=["POST"])
# def uploadAlbum(classroomId):
#     try : 
#         images = request.files.getlist("images")
        
#         album_date = request.form.get("albumDate")
#         album_title = request.form.get("albumTitle")

        
#         with engine.connect() as conn : 
#             # 반의 아이들을 모두 가져오자
#             result = conn.execute(
#                 select(user_table)
#                 .join(kid_table)
#                 .where(user_table.c.classroom_id == classroomId)
#             )
#             kid_dict = dict()
#             kid_album = dict()
#             for r in result.mappings() :
#                 kid_id = r["id"]
#                 url = r["profile"] 
#                 if url is None : continue
#                 base_url = "profile/"
#                 kid_dict[kid_id] = url
                
#                 url_parser = url.split("/")
#                 url = base_url + url_parser[-1]
#                 response = s3.get_object(Bucket = s3_bucket_name, Key= url)
#                 data = response["Body"].read()
#                 encoded_img = np.frombuffer(data, dtype = np.uint8)
#                 img = cv2.imdecode(encoded_img,cv2.IMREAD_COLOR)
                
#                 kid_dict[kid_id] = img
#                 kid_album[kid_id] = []
            
#             ### 반 아이들의 np array 추출 
#             ## >> 반 아이들의 특징 obj 추출
            

#             s3_key_list = [] 
#             image_list = []
#             class_album = []
#             s3_content_type = [] 

#             ### 파일들을 순회한다.

#             for image in images : 
                
#                 content_type = image.content_type
#                 file_name, file_extension = os.path.splitext(image.filename)

#                 file_uuid = str(uuid.uuid4())
#                 path = "album/" + file_uuid+ "." + file_extension
#                 s3_key_list.append(path)
#                 s3_content_type.append(content_type)
#                 add_object = {
#                     "file_name" : file_name,
#                     "file_path" : db_base_url + path,
#                 }
#                 #반은 일단 저장
#                 class_album.append(add_object)

#                 file_byte = np.frombuffer(image.read(),np.uint8)
#                 decoded_image = cv2.imdecode(file_byte, cv2.IMREAD_COLOR)
#                 file_obj = BytesIO(file_byte)
#                 image_list.append(file_obj)

#             ## 이부분 함수 파고들어서 개선 가능 
#                 for key, kid_image in kid_dict.items() :
#                     result = verify_image(kid_image, decoded_image)
#                     if result["verified"] :
#                         kid_album[key].append(add_object.copy())

#             ## 분류 완료 
#             # 반엘범 생성
#             created_albumid = []
#             result = conn.execute(
#                 insert(album_table),
#                 {
#                     "album_date" : album_date,
#                     "classroom_id" : classroomId,
#                     "album_title" : album_title
#                 }
#             )
#             album_id = result.inserted_primary_key[0]
#             created_albumid.append(album_id)
#             for row in class_album : 
#                 row["album_id"] = album_id
            
#             conn.execute(
#                 insert(file_table),
#                 class_album
#             )
#             # 아이앨범 생성
#             for key, value in kid_album.items() :
#                 if len(value) == 0 : continue
                
#                 result = conn.execute(
#                     insert(album_table),
#                     {
#                         "kid_id" : key, 
#                         "album_date" : album_date,
#                         "classroom_id" : classroomId,
#                         "album_title" : album_title
#                     }
#                 )
#                 album_id = result.inserted_primary_key[0]
#                 created_albumid.append(album_id)

#                 for row in value : 
#                     row["album_id"] = album_id
#                 conn.execute(
#                     insert(file_table),
#                     value
#                 )

#             ## s3업로드
#             for i, image in enumerate(image_list) :
#                 s3.upload_fileobj(image, s3_bucket_name, s3_key_list[i], ExtraArgs={'ContentType': s3_content_type[i]}  )
            
#             conn.rollback()
#             return jsonify({"status": HTTPStatus.CREATED,"message":"성공하였습니다." ,"data" : {"albumid" : created_albumid}})
#     except SQLAlchemyError as e :
#         conn.rollback()
#         return jsonify({"error" : str(e)})
#     except Exception as e:
#         return jsonify({"error" : str(e)})

@app.route("/ai/album/upload/<classroomId>", methods=["POST"])
def uploadS3(classroomId) :
    if(request.method == "POST") :
        try : 
            album_date = request.form.get("albumDate")
            album_title= request.form.get("albumTitle")
            files = request.files.getlist("images")
            with engine.connect() as conn : 
                result = conn.execute(
                    insert(album_table),
                    {
                        "album_date" : album_date,
                        "classroom_id" : classroomId,
                        "album_title" : album_title
                    }
                )
                album_id = result.inserted_primary_key[0]
                photo = []
                for file in files :
                    file_name, file_extension = os.path.splitext(file.filename)

                    file_uuid = str(uuid.uuid4())
                    path = "album/" + file_uuid + "." + file_extension
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
                return jsonify({"status" : HTTPStatus.OK , "message" : "upload success", "data" : {"albumId" : album_id}} )
        except SQLAlchemyError as sqlerror:
            print(str(sqlerror))
            return jsonify({"status" : HTTPStatus.INTERNAL_SERVER_ERROR , "message" : "interner server error"})
        except Exception as e:
            return jsonify({"status" : HTTPStatus.BAD_REQUEST , "message" : "internal server error"})

# models = [
#   "VGG-Face", 
#   "Facenet", 
#   "Facenet512", 
#   "OpenFace", 
#   "DeepFace", 
#   "DeepID", 
#   "ArcFace", 
#   "Dlib", 
#   "SFace",
# ]

# metrics = ["cosine", "euclidean", "euclidean_l2"]

# def verify_image(image1, image2):
#         '''
#         params
#         image1 : np array
#         image2 : np array
#         '''
#         result = DeepFace.verify(image1, image2, 
#                                  model_name="VGG-Face",
#                                  normalization="base", 
#                                  align=True, 
#                                  detector_backend="opencv",
#                                  enforce_detection=False,
#                                  distance_metric="euclidean_l2"
#                                  )
#             # 결과 반환
        
#         return result

if __name__ == '__main__':
    app.run(debug=True)

