import base64
import jwt
import datetime
from decouple import config
from http import HTTPStatus
from modules.db_connect import get_engine
from modules.table import get_teacher_table, get_user_table
from sqlalchemy import select
from sqlalchemy.exc import MultipleResultsFound, NoResultFound
from jwt.exceptions import ExpiredSignatureError, InvalidTokenError
## db
engine = get_engine()

jwt_secret = config("JWT_SECRET_KEY")


## table
teacher_table = get_teacher_table() 
user_table = get_user_table()


# Spring에서 생성된 JWT_SECRET과 동일한 전처리 적용


class Validator :
    
    def __init__(self, secret_key, algorithm = "HS512" ) :
        encoded_jwt_secret = base64.b64encode(secret_key.encode('utf-8')).decode('utf-8')
        self.encoded_key = base64.b64encode(encoded_jwt_secret.encode('utf-8')).decode('utf-8')
        self.algorithm = algorithm

    def validate_token(self, token : str) :
        try:
            # 토큰 검증 및 디코딩
            decoded_token = jwt.decode(token, key=self.encoded_key, algorithms=[self.algorithm])
            user_id =  decoded_token["userId"]
            exp =  decoded_token["exp"]
            # 그 유저가 선생인지 확인 >> 아니면 권한 x
            with engine.connect() as conn :
                result = conn.execute(
                    select(user_table)
                    .join(teacher_table)
                    .where(user_table.c.id == user_id)
                )
                row = result.one()
                row_dict = dict(row._asdict())
                


            # 만료 시간 확인
            expiration_time = datetime.datetime.utcfromtimestamp(exp)
            current_time = datetime.datetime.utcnow()

            if expiration_time > current_time:
                return {
                    "status" : HTTPStatus.OK,
                    "isValid" : True,
                    "message" : "유효한 토큰 입니다.",
                    "data" : row_dict,
                    "code" : "A101"
                }

            else:
                return {
                    "status" : HTTPStatus.UNAUTHORIZED,
                    "isValid" : False,
                    "message" : "만료된 토큰 입니다.",
                    "code" : "A103"
                }
        except MultipleResultsFound :
            return {
                "status" : HTTPStatus.UNAUTHORIZED,
                "isValid" : False,
                "message" : "유효하지 않는 토큰입니다.", 
                "code" : "A102"
            }
        except NoResultFound :
            return {
                "status" : HTTPStatus.FORBIDDEN,
                "isValid" : False,
                "message" : "권한이 없습니다.",
                "code" : "A106"
            }
        except ExpiredSignatureError:
            return {
                "status" : HTTPStatus.UNAUTHORIZED,
                "isValid" : False,
                "message" : "서명이 만료되었습니다.",
                "code" : "A102"
            }
        except InvalidTokenError:
            return {
                "status" : HTTPStatus.UNAUTHORIZED,
                "isValid" : False,
                "message" : "유효하지 않는 토큰입니다.",
                "code" : "A102" 
            }
        except Exception as e:
            return {
                "status" : HTTPStatus.INTERNAL_SERVER_ERROR,
                "isValid" : False,
                "message" : "internal server error"
            }
