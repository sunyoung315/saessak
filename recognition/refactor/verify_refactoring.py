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



# img_url = "image/image10.jpg"

# img = cv2.imread(img_url)
detector_backend = ['opencv']

# mtcnn 

img_list = []
for i in range(1,10) :
    img_url = f"image/kid{i}.png"
    
    img_list.append(cv2.imread(img_url,cv2.IMREAD_COLOR))        

# represent detect
start_time = time.time()

embedding_list = dict()
for i in range(len(img_list)):


    extracted_face = DeepFace.represent(
        img_path=img_list[i],
        model_name="VGG-Face",
        align=True,
        enforce_detection=False,
        normalization="VGGFace",
        detector_backend="opencv"        
    )
    embedding_list [i] = []
    for face in extracted_face :
        embedding_list[i].append(face["embedding"])        

end_time = time.time()

print("read image" , end_time- start_time)
    
# for key ,embedding in embedding_list.items() :
#     print(key)
#     for e in embedding :
#         print(e[:10])

for key1, embedding_list1 in embedding_list.items() :
    for key2, embedding_list2 in embedding_list.items() :

        for img1_representation in embedding_list1 :
            for img2_representation in embedding_list2 :

                distance_cosine = dst.findCosineDistance(img1_representation, img2_representation)
                distance_euclidean = dst.findEuclideanDistance(img1_representation, img2_representation)
                distance_euclidean_l2 = dst.findEuclideanDistance(
                    dst.l2_normalize(img1_representation), dst.l2_normalize(img2_representation)
                )

                threshold_cosine = dst.findThreshold(model_name="VGG-Face",distance_metric="cosine")
                threshold_euclidean = dst.findThreshold(model_name="VGG-Face",distance_metric="euclidean")
                threshold_euclidean_l2 = dst.findThreshold(model_name="VGG-Face",distance_metric="euclidean_l2")

                # print(f"img{key1} and img{key2} result :     ")
                # print(f"cosine [{True if distance_cosine <= threshold_cosine else False}]")
                # print(f"eculidean [{True if distance_euclidean <= threshold_euclidean else False}]")
                # print(f"eculidean_l2 [{True if distance_euclidean_l2 <= threshold_euclidean_l2 else False}]")
                # print()

toc = time.time() 

print("calculate" , toc - end_time)