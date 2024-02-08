from deepface import DeepFace

img_url = "/app/modules/image/image1.png"
model = "VGG-Face"
normal = "VGGFace2"
result = DeepFace.represent(
    img_path=img_url,
    align=True,
    detector_backend="mtcnn",
    enforce_detection=False,
    model_name=model,
    normalization=normal
)

print("download model")


