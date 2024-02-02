# ⭐️ 새싹일기

> SSAFY 10기 2학기 공통 프로젝트 - 새싹일기 <br/> > https://i10a706.p.ssafy.io/

- 기간: 2024.01.03 ~ 2024.02.16
- 주제: 우리 아이 어린이집 생활 공유 서비스
- 개요: `새싹일기`는 어린이집 관리 및 부모와의 소통을 위한 웹 서비스입니다.

## 👐 주요 기능 👐

> 알림 및 출석 관리

- 교사와 부모 간의 실시간 알림 서비스를 통해 아이들의 등원 및 퇴원 여부를 효과적으로 전달합니다. 이를 통해 부모는 아이가 안전하게 학교에 도착했는지 확인하고, 교사는 출석을 쉽게 기록할 수 있습니다.

> 알림장 서비스

- 기존 알림장 작성과 조회뿐만 아니라, 성장 기록을 시각적으로 확인할 수 있는 차트 기능을 제공합니다. 또한, 알림장의 주간 요약 레포트를 작성하여 부모가 간편하게 아이의 주요 활동과 발전 상황을 파악할 수 있습니다.

> 사진 AI 자동 분류 및 공유 서비스

- 교사가 업로드한 사진을 AI가 자동으로 분류하여 각 원아의 얼굴을 인식하고 구분된 앨범에 사진을 정리합니다. 이를 통해 부모는 아이의 일상을 더욱 쉽게 관찰할 수 있습니다.

> 동의서

- 귀가 동의서, 식품 알레르기 동의서 등을 간편히 작성하고 전자서명 기능을 통해 부모의 동의를 얻습니다. 이로써 중요한 정보에 대한 부모의 이해와 협조를 효과적으로 이끌어낼 수 있습니다.

> 식단표

- 교사가 제공된 식단표를 기반으로 사진을 등록하고, 알레르기 표시에 따라 해당 음식에 주의가 필요한 아이에게 부모에게 실시간으로 알림을 제공합니다. 이를 통해 아이들의 건강과 안전을 최우선으로 고려합니다.

> 화상화면 및 채팅 서비스

- 학부모와 교사 간의 효과적인 소통을 위한 채팅 기능과 필요 시 openvidu를 활용한 화상채팅을 통해 언제든지 상호작용이 가능하도록 합니다. 이를 통해 교육 과정 및 아이의 상태에 대한 이해를 깊게 나눌 수 있습니다.
  <br/>

## 👐 팀원 구성 및 역할 분담👐

<table align="center">
    <tr> 👀 FrontEnd</tr>
    <tr align="center">
        <td style="width: 250px;">
            <a href="https://github.com/manju0329">
              <img src="https://avatars.githubusercontent.com/u/68678911?v=4" width="100">
              <br />
              <b>이민주 (팀장)</b>
            </a>
        </td>
        <td style="width: 250px">
            <a href="https://github.com/sunyoung315">
              <img src="https://avatars.githubusercontent.com/u/139304927?v=4" width="100">
                <br />
              <b>김선영 (팀원)</b>
            </a> 
        </td>
         <td style="width: 250px">
            <a href="https://github.com/Joni1995">
              <img src="https://avatars.githubusercontent.com/u/139304892?v=4" width="100">
                <br />
              <b>김종인 (팀원)</b>
            </a> 
        </td>
    </tr>
    <tr align="left">
        <td>
        1. 프로젝트 일정 관리<br>
        2. FE WebSocket 화상채팅 구현<br>
        3. FE 로그인 기능 구현<br>
        </td>
        <td>
        1. 아이디어 주제 기획 및 선정<br>
        2. FE 알림장 기능 구현<br>
        3. FE 출석부 기능 구현<br>
        </td>
        <td>
        1. Vue Layout 설계<br>
        2. FE 앨범 기능 구현<br>
        3. FE 동의서 기능 구현<br>
        </td>
    </tr>
</table>
<table align="center">
    <tr> 🕶 BackEnd</tr>
    <tr align="center">
        <td style="width: 250px;">
            <a href="https://github.com/haeun-i">
              <img src="https://avatars.githubusercontent.com/u/76279010?v=4" width="100">
              <br />
              <b>김하은 (팀원)</b>
            </a>
        </td>
        <td style="width: 250px;">
            <a href="https://github.com/0o0mlb">
              <img src="https://avatars.githubusercontent.com/u/81353217?v=4" width="100">
                <br />
              <b>윤선아 (팀원)</b>
            </a> 
        </td>
         <td style="width: 250px;">
            <a href="https://github.com/Leeminw">
              <img src="https://avatars.githubusercontent.com/u/92208022?v=4" width="100">
                <br />
              <b>이민우 (팀원)</b>
            </a> 
        </td>
    </tr>
     <tr align="left">
        <td>
        1. BE 총괄<br>
        2. 인프라 설정<br>
        3. 채팅 서버 구현<br>
        </td>
        <td>
        1. BE 알림 기능 구현<br>
        2. BE 전자 서명 구현<br>
        3. 소셜 로그인 기능 구현<br>
        </td>
        <td>
        1. BE 사진 분류 API 작성<br>
        2. FLASK 서버 관리<br>
        3. 앨범/게시판 API 작성
        </td>
    </tr>
</table>

## 👐 프로젝트 산출물 👐

### [Figma](https://www.figma.com/file/ACANoSkPAK37VNyMIMjHNl/%EC%83%88%EC%8B%B9%EC%9D%BC%EA%B8%B0?type=design&node-id=0-1&mode=design&t=zhn7Jt6eixMXWhoA-0)

📌 부모님 화면
![Figma](/assets/parentView.png)

📌 선생님 화면
![Figma](/assets/teacherView.png)

### [ERD](https://www.erdcloud.com/d/jzdAxd3ySZ8tXDa5n)

![ERD](/assets/ERD.png)

### [API 명세서](https://spiny-nylon-a25.notion.site/API-52047430168d4934ba76a379369aa596?pvs=4)

![api](/assets/api1.png)
![api](/assets/api2.png)

### 아키텍처

![image](/uploads/5c3dc1046b7778ac12341896a922fd2e/image.png)

### [포팅 매뉴얼](https://spiny-nylon-a25.notion.site/607d2233ca3d498caaad1617cc111ed3?pvs=4)

<br/>

## 👐 진행 상황 👐

### **프론트엔드**

- 이민주
  - 카카오 로그인 백엔드와 연결
  - WebSocket, Stomp를 사용한 기본 채팅 기능 구현
  - 채팅 기능에 JWT 토큰을 통한 인증 추가(프론트)
  - JWT 토큰을 사용한 채팅 송수신자 구분 기능 구현
  <hr>
- 김선영
  - 출석부
    - 선생님ver 화면 구성 및 기능 구현
      - 데이트 피커로 해당 월 주차 계산하여 주차별 테이블
      - 당일은 등/하원 버튼 → 클릭 시 등록된 학부모에게 알림 → 등원 시간으로 버튼 변경
      - 데이터 정보에 따라 시간/미입력/결석 표시
    - 학부모ver 화면 구성 및 기능 구현
      - vue-cal 라이브러리
      - 달력으로 아이 등/하원 시간 조회
  - 알림장
    - 선생님ver 리스트, 상세조회, 작성 화면 구성 및 기능 구현
      - 반 아이들 조회 → 알림장 리스트(이름, 프로필 사진)
      - 한 아이의 알림장 조회 시 가장 최근 알림장 상세 조회 + 데이트피커로 다른 날짜의 알림장 조회
      - 알림장 내용 및 건강 기록 작성
    - 학부모ver 리스트, 상세조회 화면 구성 및 기능 구현
      - 날짜별로 리스트 조회
      - 알림장 상세조회 + 데이트피커로 다른 날짜의 알림장 조회
      - 성장 기록 확인 차트 라이브러리
      - 요약레포트(OpenAI API) 기능 구현 중(생성 시간 문제 해결 필요)…
  - 식단표
    - 주차별 식단표 조회 레이아웃 구성 중…
    <hr>
- 김종인
  - 앨범
    - 앨범 선생님ver 화면 구성 및 기능 구현
      - 파일 다중 업로드 기능(s3)
      - Carousel, Card 형식 화면 구성
      - DatePicker에 선택된 앨범 조회 기능
      - 선택된 아이에 따른 상세 앨범 조회 기능
    - 앨범 학부모ver 화면 구성 및 기능 구현
      - 파일 다중 선택 및 다운로드 기능(s3)
      - Carousel, Card 형식 화면 구성
      - DatePicker에 선택된 앨범 조회
      - 내 아이/반 전체 상세 앨범 조회 기능
  - 동의서
    - 전자서명 기능 구현 (현재 백과 연동되지 않고 프론트에서만 적용)
    - 귀가 동의서 화면 구성 및 더미 데이터를 적용한 기능 구현
    - 식품 알레르기 동의서 화면 구성 및 더미 데이터를 적용한 기능 구현
  - 메인 페이지
    - 메인 페이지 로그인 전 화면 구현 완료
    - 메인 페이지 로그인 후(교사/부모) 화면 레이아웃 설계

### **백엔드**

- 김하은
  - 인프라 설정 완료
    - NginX Reverse Proxy 설정
    - Jenkins-Gitlab Webhook 설정
    - ssl 인증서 발급 후 https 연결
  - 채팅 기능 구현
    - WebSocket, Stomp를 사용한 채팅 서버 구현
    - 채팅 내역 Redis 캐싱 구현 중
  - 공지사항 API 작성
  <hr>
- 이민우
  - deepface를 이용한 얼굴 사진 분류 API작성
    - 사진속 얼굴들을 찾고, 얼굴의 embedding을 추출하는 함수 작성
    - 반 아이의 embedding과 사진속 embedding을 비교하여 아이별 엘범에 저장하는 API 작성
  - flask 서버 jwt 를 이용한 권한 관리
    - 헤더에서 받은 accessToken을 이용한 인증 관련 서비스 구현
  - 엘범 API 작성
  - 게시판 API 작성
  - 엘범, 게시판 프론트와 연동중
  <hr>
- 윤선아
  - FCM
    - 등하원 알림
    - 식품 알러지 알림 ( scheduling )
    - 대리인 귀가 알림
  - 소셜로그인
    - OAuth2 Kakao 로그인 기능 구현
    - BASE64 암호코드를 활용한 회원가입 (학부모는 kid, 선생님은 classroomid)
    - accessToken, refreshToken JWT로 암호화
    - refreshToken는 Redis 저장
    - accessToken으로 Authentication 인증 관련 서비스 구현
    - SpringSecurity, JwtAuthenticationFilter으로 401 Error 처리
  - JPA CRUD 구현
    - 의뢰서, 식단표, 출석부, 알람, 설정, 유저
  - 식단메뉴, 유저프로필에 대한 사진 AWS S3 upload

<br/>

## 👐 🏞 Git Flow 👐

git flow는 각자 맡은 기능에 맞게 `feature` 브랜치를 생성하고, 완료된 기능은 `develop에` `merge하여` 사용했습니다. 충돌 상황을 최소화하고자 매일 오후 퇴실 전 스크럼에 각자 작업한 기능을 git에 `push`하였습니다.

또한 `commit message` 는 `[feature/역할/기능] git 컨벤션`과 같이 통일하여 작성했습니다.

```Plain Text
Feat : 새로운 기능을 추가하는 경우
Fix : 버그를 고친경우
Docs : 문서를 수정한 경우
Style : 코드 포맷 변경, 세미콜론 누락, 코드 수정이 없는경우
Refactor : 코드 리팩토링
Test: 테스트 코드 작성
```

## 👐 📦 폴더 구조 👐

<details>
<summary>FE 폴더 구조</summary>
<div markdown="1">

```
📦frontend
 ┗ 📂saessak
 ┃ ┣ 📂.vscode
 ┃ ┃ ┗ 📜extensions.json
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂api
 ┃ ┃ ┣ 📂assets
 ┃ ┃ ┣ 📂components
 ┃ ┃ ┃ ┣ 📂album
 ┃ ┃ ┃ ┣ 📂attendance
 ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┣ 📂chat
 ┃ ┃ ┃ ┣ 📂common
 ┃ ┃ ┃ ┣ 📂document
 ┃ ┃ ┃ ┣ 📂menu
 ┃ ┃ ┃ ┣ 📂notice
 ┃ ┃ ┃ ┣ 📂setting
 ┃ ┃ ┃ ┗ 📂user
 ┃ ┃ ┣ 📂router
 ┃ ┃ ┣ 📂store
 ┃ ┃ ┣ 📂views
 ┃ ┃ ┣ 📜App.vue
 ┃ ┃ ┗ 📜main.js
 ```

</div>
</details>

<details>
<summary>BE 폴더 구조</summary>
<div markdown="1">

```
📦backend
 ┗ 📂saessak
 ┃ ┣ 📂gradle
 ┃ ┃ ┗ 📂wrapper
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂ssafy
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂saessak
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂alarm
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂album
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂attendance
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂board
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂chat
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂handler
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂document
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂code
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂fcm
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂growth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂menu
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂oauth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂authentication
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂client
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂kakao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂jwt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂filter
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂token
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂result
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂s3
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SaessakApplication.java
 ```

</div>
</details>
