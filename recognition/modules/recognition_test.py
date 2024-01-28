import face_recognition

embed_list = []
for i in range(1,9) :
    base_url = f"image/kid{i}.png"
    image_file = face_recognition.load_image_file(base_url)
    encodings = face_recognition.face_encodings(image_file)
    if len(encodings) == 0 : 
        print(i)
        continue
    embed_list.append(face_recognition.face_encodings(image_file, num_jitters=10)[0])

face_recognition.compare_faces
for i, embed in enumerate(embed_list):
    print(i, face_recognition.compare_faces(embed_list, embed, 0.5))