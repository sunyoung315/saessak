import face_recognition

person = face_recognition.load_image_file("/extracted/face0.jpg")
print(type(person))
my_face_encoding = face_recognition.face_encodings(person)


unknown = face_recognition.load_image_file("extracted/face2.jpg")
unknown_encoding = face_recognition.face_encodings(unknown)[0]

##  compare_face  : ( 알고 있는 encoidng 리스트,  검증 encode, torlerance >> 튜닝 할 수 있다. )
result = face_recognition.compare_faces(my_face_encoding,unknown_encoding,tolerance=0.6 )

similar = face_recognition.face_distance(my_face_encoding, unknown_encoding)

print(result)