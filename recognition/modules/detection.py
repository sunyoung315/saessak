import face_recognition
import cv2
from PIL import Image
import numpy as np

## 얼굴의 위치를 찾아 준다.

image = face_recognition.load_image_file("uploadedImage/movie.png")

face_locations = face_recognition.face_locations(image, model = "cnn")

print(len(face_locations))

sumList = [] 

for i, face in enumerate(face_locations): 
    top, right, bottom, left = face
    
    face_image = image[top:bottom,left:right]
    
    added_face = cv2.resize( face_image , (200,200))
    cv2.imwrite("extracted/face" + str(i) +".jpg", added_face)
    sumList.append(added_face)


addv = cv2.vconcat(sumList)
cv2.imshow("vertical" , addv)

cv2.waitKey()
