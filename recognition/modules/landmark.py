import face_recognition
import cv2
from PIL import Image


## 얼굴의 랜드마크를 표시한다.
image = face_recognition.load_image_file("uploadedImage/test.png")

landmarkList = face_recognition.face_landmarks(image)


for landmark in landmarkList: 
    # print(landmark)
    print()
    for (key, value) in landmark.items() : 
        print(key, value)
        for (x,y) in value :
            image[y][x] = [0,0,255]

cv2.imshow("landmark" , image)

cv2.waitKey()