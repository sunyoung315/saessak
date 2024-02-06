from flask import Flask, render_template, request, jsonify
from deepface import DeepFace
import base64
app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/verify', methods=['POST'])
def verify():
    try:
        # 새로운 디렉토리로 이동
        
        # POST 요청에서 이미지 데이터를 가져옴
        image1_data = request.files['image1'].read()
        image2_data = request.files['image2'].read()
    

        # 이미지 데이터를 base64 문자열로 변환
        image1_base64 = "data:image/png;base64," + base64.b64encode(image1_data).decode('utf-8')
        image2_base64 = "data:image/png;base64," + base64.b64encode(image2_data).decode('utf-8')
        print(len(image1_base64))
        print(len(image2_base64))
        # 이미지 간의 얼굴 유사성 계산
        result = DeepFace.verify(image1_base64, image2_base64)

            # 결과 반환
        return jsonify(result)
    except Exception as e:
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    app.run(debug=True)
