from deepface import DeepFace

img_url = "/app/modules/image/image1.png"

result = DeepFace.represent(
    img2_path=img_url,
    align=True,
    detector_backend="opencv",
    enforce_detection=False,
    model_name="VGG-Face",
    normalization="VGGFace2"
)

print("download model")


