from sqlalchemy.orm import declarative_base, relationship
from sqlalchemy import Integer, Column, String, Date, ForeignKey
from sqlalchemy.sql import func
Base = declarative_base()

class Album(Base):
    __tablename__ = 'album'

    album_id = Column(Integer, primary_key= True)
    classroom_id = Column(Integer)
    kid_id = Column(Integer)
    album_title = Column(String(100))
    album_date = Column(Date,  server_default=func.now())

    def __repr__(self):
        return f"Album(title = {self.album_title} , date = {self.album_date})"

class File(Base) :
    __tablename__ = 'file'
    file_id = Column(Integer, primary_key=True)
    album_id = Column(Integer, ForeignKey("album.album_id"))
    file_name = Column(String(100))
    file_path = Column(String(300))
    file_uuid = Column(String(100))

