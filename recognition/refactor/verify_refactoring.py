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
from deepface.commons import functions, distance as dst
import time


img_url = ["image/image2.jpg","image/image4.jpg"]

# img = cv2.imread(img_url)
detector_backend = ['opencv']

# mtcnn 

models = [
  "VGG-Face", 
  "Facenet", 
  "Facenet512", 
  "OpenFace", 
  "DeepFace", 
  "DeepID", 
  "ArcFace", 
  "SFace",
]

normals = [
    'base', 'raw', 'Facenet', 'Facenet2018', 'VGGFace', 'VGGFace2', 'ArcFace'
]
embedding_list = []

model = "Facenet512"
normal = "VGGFace2"


r = 255
index = 1
# print(extracted_face)
for url in img_url :
    img = cv2.imread(url)
    extracted_face = DeepFace.represent(
    img_path=url,
    model_name=model,
    align=True,
    enforce_detection=False,
    normalization=normal,
    detector_backend="mtcnn"        
)

    for face in extracted_face :
        area = face["facial_area"]
        x = area["x"]
        y = area["y"]
        w = area["w"]
        h = area["h"]
        cv2.putText(img,str(index), (x,y),cv2.FONT_HERSHEY_PLAIN, fontScale=2 , color=(255,255,255), thickness=2)
        cv2.rectangle(img, (x,y,w,h), color=(r,0,r))
        index+= 1
        # print(face)
        embedding_list.append(face["embedding"])        

    cv2.imshow(f"tt{index}" , img)
    cv2.imwrite(filename=f"detected{index}.jpg", img=img)
# for key ,embedding in embedding_list.items() :
#     print(key)
#     for e in embedding :
#         print(e[:10])

# for key1, embedding_list1 in embedding_list.items() :
#     for key2, embedding_list2 in embedding_list.items() :

#         for img1_representation in embedding_list1 :
#             for img2_representation in embedding_list2 :

                # distance_cosine = dst.findCosineDistance(img1_representation, img2_representation)
                # distance_euclidean = dst.findEuclideanDistance(img1_representation, img2_representation)

                # threshold_cosine = dst.findThreshold(model_name="VGG-Face",distance_metric="cosine")
                # threshold_euclidean = dst.findThreshold(model_name="VGG-Face",distance_metric="euclidean")
                # distance_euclidean_l2 = dst.findEuclideanDistance(
                #     dst.l2_normalize(img1_representation), dst.l2_normalize(img2_representation)
                # )
                # threshold_euclidean_l2 = dst.findThreshold(model_name="VGG-Face",distance_metric="euclidean_l2")

                # print(f"img{key1} and img{key2} result :     ")
                # print(f"cosine [{True if distance_cosine <= threshold_cosine else False}]")
                # print(f"eculidean [{True if distance_euclidean <= threshold_euclidean else False}]")
                # print(f"eculidean_l2 [{True if distance_euclidean_l2 <= threshold_euclidean_l2 else False}]")
                # print()
index = 0
for embed1 in embedding_list : 
    index += 1
    inner = 1
    print("비교대상" , index)
    for embed2 in embedding_list :
        print(inner)
        inner += 1
        distance_cosine = dst.findCosineDistance(embed1, embed2)
        distance_euclidean = dst.findEuclideanDistance(embed1, embed2)

        threshold_cosine = dst.findThreshold(model_name=model,distance_metric="cosine")
        threshold_euclidean = dst.findThreshold(model_name=model,distance_metric="euclidean")
        distance_euclidean_l2 = dst.findEuclideanDistance(
            dst.l2_normalize(embed1), dst.l2_normalize(embed2)
        )
        threshold_euclidean_l2 = dst.findThreshold(model_name=model,distance_metric="euclidean_l2")

        # print(f"img{key1} and img{key2} result :     ")
        print(f"cosine [{True if distance_cosine <= threshold_cosine else False}]")
        print(f"eculidean [{True if distance_euclidean <= threshold_euclidean else False}]")
        print(f"eculidean_l2 [{True if distance_euclidean_l2 <= threshold_euclidean_l2 else False}]")
        print()
cv2.waitKey()