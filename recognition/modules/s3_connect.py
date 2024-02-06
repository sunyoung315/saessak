import boto3
from botocore.client import Config
from decouple import config
s3_access_key = config("s3_access_key")
s3_secret_key = config("s3_secret_key")
s3_bucket_name = config("s3_bucket_name")
s3_region_name = config("s3_region_name")
db_base_url = f"https://{s3_bucket_name}.s3.{s3_region_name}.amazonaws.com/"
connector = boto3.client(
    's3',
    aws_access_key_id=s3_access_key,
    aws_secret_access_key=s3_secret_key,
    config=Config(signature_version='s3v4')
)

def get_s3_connector() :
    return connector