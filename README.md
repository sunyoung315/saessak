# ⭐️ 새싹일기

> SSAFY 10기 2학기 공통 프로젝트 - 새싹일기 <br/> > https://i10a706.p.ssafy.io/

- 기간: 2024.01.03 ~ 2024.02.16
- 주제: 우리 아이 어린이집 생활 공유 서비스
- 개요: `새싹일기`는 어린이집 관리 및 부모와의 소통을 위한 웹 서비스입니다.

## 👐 서비스 소개 👐

### 💻 메인페이지 및 로그인

사이트에 접속하면 새싹일기 기능에 대한 가이드를 확인할 수 있습니다. <br>
로그인 후에는 오늘 날짜의 데이터를 확인할 수 있는 대시보드를 통해 간편하게 서비스를 이용할 수 있습니다. <br>

<details>
<summary>서비스 화면</summary>
<div>

![메인페이지](/assets/메인화면 로그인.gif)
</div>
</details>
<br>

### 📌 공지사항

유치원의 공지사항을 등록하고, 중요한 공지사항을 고정하여 메인페이지에서 편하게 확인할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![공지사항](/assets/공지사항.gif)
</div>
</details>
<br>

### 📕 알림장 서비스

기존 알림장 작성과 조회뿐만 아니라, 성장 기록을 시각적으로 확인할 수 있는 차트 기능을 제공합니다.<br>
또한, chatGPT를 통해 요약 레포트를 작성하여 부모가 간편하게 아이의 주요 활동과 발전 상황을 파악할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![알림장](/assets/알림장.gif)
</div>
</details>
<br>

### 🎬 사진 AI 자동 분류 및 공유 서비스

교사가 업로드한 사진을 AI가 자동으로 분류하여 각 원아의 얼굴을 인식하고 구분된 앨범에 사진을 정리합니다.<br>
이를 통해 부모는 아이의 일상을 더욱 쉽게 관찰할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![앨범](/assets/앨범.gif)
</div>
</details>
<br>

### 📃 동의서

귀가 동의서, 식품 알레르기 동의서 등을 간편히 작성하고 전자서명 기능을 통해 부모의 동의를 얻습니다.<br>
이로써 중요한 정보에 대한 부모의 이해와 협조를 효과적으로 이끌어낼 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![동의서](/assets/동의서.gif)
</div>
</details>
<br>

### 🍱 식단표

교사가 제공된 식단표를 기반으로 사진을 등록하고, 알레르기 표시에 따라 해당 음식에 주의가 필요한 아이에게 교사에게 실시간으로 알림을 제공합니다.<br>
이를 통해 아이들의 건강과 안전을 최우선으로 고려합니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![식단표](/assets/식단표.gif)
</div>
</details>
<br>

### 💡 알림 및 출석 관리

교사와 부모 간의 실시간 알림 서비스를 통해 아이들의 등원 및 퇴원 여부를 효과적으로 전달합니다.<br>
이를 통해 부모는 아이가 안전하게 학교에 도착했는지 확인하고, 교사는 출석을 쉽게 기록할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![출석부](/assets/출석부.gif)
</div>
</details>
<br>

### 🎭 화상화면 및 채팅 서비스

학부모와 교사 간의 효과적인 소통을 위한 채팅 기능과 필요 시 openvidu를 활용한 화상채팅을 통해 언제든지 상호작용이 가능하도록 합니다.<br>
이를 통해 교육 과정 및 아이의 상태에 대한 이해를 깊게 나눌 수 있습니다.<br/>

<details>
<summary>서비스 화면</summary>
<div>

![채팅](/assets/채팅.gif)
</div>
</details>
<br>

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
        1. 소셜 로그인 기능 구현<br>
        2. 화상화면 및 채팅 기능 구현<br>
        3. JIRA 관리<br>
        </td>
        <td>
        1. Figma (UI/UX)<br>
        2. 메인 / 알림장 페이지 구현<br>
        3. 식단표 / 출석부 페이지 구현<br>
        </td>
        <td>
        1. 앨범 페이지<br>
        2. 동의서 페이지<br>
        3. 다운로드, 전자서명 기능 구현<br>
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
        1. 인프라 구현<br>
        2. 화상화면 및 채팅기능 구현<br>
        3. 공지사항 API 기능 구현<br>
        </td>
        <td>
        1. 알림 기능 구현<br>
        2. 소셜 로그인 기능 구현<br>
        3. 식단표 / 동의서 / 출석부 기능 구현<br>
        </td>
        <td>
        1. AI 사진 분류 모델 학습<br>
        2. 알림장 API 기능 구현<br>
        3. 앨범 API 기능 구현<br>
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

<details>
<summary>자세히 보기</summary>
<div markdown="1">

![api](/assets/api.png)
</div>
</details>

### 아키텍처

![image](/assets/architecture.png)

### [포팅 매뉴얼](https://spiny-nylon-a25.notion.site/607d2233ca3d498caaad1617cc111ed3?pvs=4)

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
 ┃ ┃ ┣ 📜AppView.vue
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

<details>
<summary>AI 폴더 구조</summary>
<div markdown="1">

```
📦recognition   
 ┗ 📂deepface    
 ┗ 📂modules 
 ┗ 📂refactor     
 ┗ 📜deepface_server.py   
```
</div>
