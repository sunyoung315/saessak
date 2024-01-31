from sqlalchemy import MetaData, ForeignKey
from sqlalchemy import Table, Column, Integer, String, Date

metadata = MetaData()


classroom_table = Table(
    "classroom",
    metadata,
    Column("classroom_id", primary_key=True)
)

album_table = Table(
    "album",
    metadata,
    Column("album_id", Integer, primary_key=True),
    Column("classroom_id", ForeignKey("classroom.classroom_id"), nullable=True),
    Column("kid_id", ForeignKey("user.id"), nullable=True),
    Column("album_title", String),
    Column("album_date", Date)
)

file_table = Table(
    'file',
    metadata,
    Column("file_id",Integer,primary_key=True),
    Column("album_id", ForeignKey("album.album_id"),nullable=False),
    Column("file_name", String),
    Column("file_path", String)
)

kid_table = Table(
    "kid",
    metadata,
    Column ("id", ForeignKey("user.id"), nullable=False)
)

teacher_table = Table(
    "teacher",
    metadata,
    Column("id", ForeignKey(""))
)

user_table = Table(
    "user",
    metadata,
    Column("id",Integer, primary_key=True),
    Column("profile", String),
    Column("classroom_id",ForeignKey("classroom_id"), nullable=False)
)

def get_user_table() :
    return user_table

def get_album_table() :
    return album_table

def get_file_table() : 
    return file_table

def get_kid_table():
    return kid_table

def get_teacher_table():
    return teacher_table
