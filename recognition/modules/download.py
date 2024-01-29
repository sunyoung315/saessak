from deepface import DeepFace

img_url = "/app/modules/image/image1.png"

result = DeepFace.verify(
    img1_path=img_url,
    img2_path=img_url,
    align=True,
    detector_backend="opencv",
    distance_metric="euclidean_l2",
    enforce_detection=False,
    model_name="VGG-Face",
    normalization="base"
)

print("download model" , result["verified"])
