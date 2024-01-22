from sqlalchemy import MetaData, ForeignKey
from sqlalchemy import Table, Column, Integer, String, Date

metadata = MetaData()

album_table = Table(
    "album",
    metadata,
    Column("album_id", Integer, primary_key=True),
    Column("classroom_id", ForeignKey("classroom.classroom_id"), nullable=True),
    Column("kid_id", ForeignKey("kid.kid_id"), nullable=True),
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

face_feature_table = Table(
    "face_feature",
    metadata,
    Column("face_feature_id", Integer, primary_key=True),
    Column("feature", String)

)

kid_table = Table(
    "kid",
    metadata,
    Column("kid_id",Integer, primary_key=True),
    Column("kid_profile", String),
    Column("classroom_id",ForeignKey("classroom_id"), nullable=False)

)

def get_kid_table() :
    return kid_table

def get_album_table() :
    return album_table

def get_file_table() : 
    return file_table

def get_feature_table() : 
    return face_feature_table