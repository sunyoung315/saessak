from deepface import DeepFace
import cv2
import numpy as np
from deepface.modules import (
    modeling,
    representation,
    verification,
    recognition,
    demography,
    detection,
    realtime,
)
from deepface.commons import functions

img_url = "image/image10.jpg"

img = cv2.imread(img_url)
detector_backend = ['opencv', 'retinaface',
            'mtcnn', 'ssd']

#mtcnn 성능 좋다. 이거써야할듯?



img_list = []
for _ in detector_backend :
    img_list.append(img.copy())


np_array1 = np.array(img,np.uint8)
# np_array2 = np.array(cv2.imread(img2), np.uint8)

# face detect
for i in range(len(detector_backend)):


    extracted_face = DeepFace.extract_faces(
        img_path=img_list[i],
        enforce_detection=False,
        detector_backend=detector_backend[i]        
    )
    for face in extracted_face :
        image_region = face["facial_area"]
        x = image_region["x"]
        y = image_region["y"]
        w = image_region["w"]
        h = image_region["h"]
        cv2.rectangle(img=img_list[i],rec=(x,y,w,h),color=(0,255,0),thickness=1)

    cv2.imshow(detector_backend[i], img_list[i])
cv2.waitKey()