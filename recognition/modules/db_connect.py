from decouple import config
from sqlalchemy import create_engine

db_dialect = config("db_dialect")
db_user = config ("db_user")
db_password = config("db_password")
db_url= config("db_url")
db_name= config("db_name")

db_connect_url = f"{db_dialect}://{db_user}:{db_password}@{db_url}/{db_name}"
engine = create_engine(db_connect_url, pool_size=20, max_overflow=0, echo=True, future=True)


def get_engine() :
    return engine