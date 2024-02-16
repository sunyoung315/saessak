from deepface import DeepFace
## 함수를 최초 실행시 모델을 다운로드받는 과정이 포함된다.
img_url = "/app/modules/image/image1.png"
model = "SFace"
normal = "Facenet2018"
detector = "opencv"
result = DeepFace.represent(
    img_path=img_url,
    align=True,
    detector_backend="opencv",
    enforce_detection=False,
    model_name=model,
    normalization=normal
)

print("download model")


