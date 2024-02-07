from deepface import DeepFace

img_url = "/app/modules/image/image1.png"

result = DeepFace.represent(
    img_path=img_url,
    align=True,
    detector_backend="opencv",
    enforce_detection=False,
    model_name="Facenet512",
    normalization="Facenet2018"
)

print("download model")


