# 🌱 새싹일기

> SSAFY 10기 2학기 공통 프로젝트 - 새싹일기 <br/> 
> https://i10a706.p.ssafy.io/

- 기간: 2024.01.03 ~ 2024.02.16
- 주제: 우리 아이 어린이집 생활 공유 서비스
- 개요: <b>`새싹일기`</b>는 어린이집 관리 및 부모와의 소통을 위한 웹 서비스입니다.

## 👐 서비스 소개 👐

### 💻 메인페이지 및 로그인

사이트에 접속하면 새싹일기 서비스 `이용 가이드`를 확인할 수 있습니다. <br>
로그인 후에는 각 유저에게 맞는 오늘 날짜의 데이터를 확인할 수 있는 `대시보드`를 제공합니다. <br>

<details>
<summary>서비스 화면</summary>
<div>

![메인페이지](/assets/00.메인페이지.gif)
</div>
</details>
<br>

### 📢 공지사항

선생님은 유치원의 공지사항을 첨부파일과 함께 등록할 수 있습니다.<br>
학부모님은 중요한 공지사항을 `즐겨찾기`하여 상단에 고정하고, 대시보드에서 쉽게 확인할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![공지사항](/assets/01.공지사항.gif)
</div>
</details>
<br>

### 📒 알림장

선생님은 아이의 하루를 작성하고, 선택적으로 건강기록을 함께 등록할 수 있습니다.<br>
(해당 날짜에 등록된 앨범이 있으면 알림장의 커버사진으로 자동으로 등록됩니다.)<br>
학부모님은 `성장 기록 확인` 에서 알림장에 기록된 키, 몸무게 데이터를 토대로 그려진 차트를 통해 아이의 성장 속도를 확인할 수 있습니다.<br>
또한, `요약 레포트` 에서 2주 내 기간을 선택하면, 아이의 어린이집 생활을 간편하게 확인할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![알림장](/assets/02.알림장.gif)
</div>
</details>
<br>

### 🎞️ 앨범

선생님은 간편하게 반 아이들의 사진을 날짜별로 제목과 사진을 1번만 등록하면 됩니다.<br>
업로드된 사진을 `AI`가 아이들의 얼굴을 인식하여 자동으로 분류하고, `아이별 앨범을 생성`합니다.<br>
이를 통해 학부모님은 그 날의 반 앨범에서 내 아이의 사진만 손쉽게 확인하고 다운로드 받을 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![앨범](/assets/03.앨범.gif)
</div>
</details>
<br>

### 📃 동의서

학부모님은 귀가 동의서와 식품 알레르기 동의서를 간편하게 작성할 수 있습니다.<br>
선생님은 `전자서명`을 통해 신뢰있는 서류 관리가 가능하며, 선생님의 확인여부를 학부모님이 확인할 수 있습니다.<br>
귀가 동의서를 작성하면 선생님에게 실시간 일림을 보내 바로 확인할 수 있습니다.<br>


<details>
<summary>서비스 화면</summary>
<div>

![동의서](/assets/04.동의서.gif)
</div>
</details>
<br>

### 🍱 식단표

선생님은 제공된 식단표를 기반으로 일별로 음식명과 알레르기 식품 정보를 등록하고, 식단 사진은 당일 추가로 등록할 수 있습니다.<br>
또한, `식품 알레르기 동의서에 있는 성분`이 오늘의 식단표에 있다면, 점심/간식 시간에 `실시간 알림`을 보내 선생님이 해당 아이의 식단을 잊지 않고 확인할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![식단표](/assets/05.식단표.gif)
</div>
</details>
<br>

### 🛎️ 출석부

선생님이 반 아이들의 등/하원 시 버튼을 누르면, 학부모님은 아이의 `등/하원 시간을 실시간 알림`으로 전달받을 수 있습니다.<br>
선생님은 반 아이들의 출석 현황을 테이블 형식으로, 학부모님은 내 아이의 출석 현황을 캘린더 형식으로 확인할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![출석부](/assets/06.출석부.gif)
</div>
</details>
<br>

### 👩‍👧‍👦 아이 등록

선생님은 우리반 관리 메뉴를 통해 아이를 등록하고, 발급되는 `등록코드`를 학부모님에게 전달합니다.<br>
(등록된 프로필 사진이 앨범의 AI 사진 분류에 이용됩니다.)<br>
학부모님은 가입 혹은 추가로 아이를 등록할 때, 전달받은 코드를 입력하면 아이가 추가됩니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![채팅](/assets/07.우리반%20관리.gif)
</div>
</details>
<br>

### 💬 채팅

선생님과 학부모님은 채팅을 통해 전화번호 노출 없이 소통을 할 수 있습니다.<br>
또한, 상황에 따라 필요 시, 선생님은 학부모에게 화상 채팅을 요청할 수 있습니다.<br>

<details>
<summary>서비스 화면</summary>
<div>

![채팅](/assets/08.채팅.gif)
</div>
</details>
<br>
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
<br>

## 👐 프로젝트 산출물 👐

### [Figma](https://www.figma.com/file/ACANoSkPAK37VNyMIMjHNl/%EC%83%88%EC%8B%B9%EC%9D%BC%EA%B8%B0?type=design&node-id=0-1&mode=design&t=zhn7Jt6eixMXWhoA-0)

📌 부모님 화면
![Figma](/assets/parentView.png)

📌 선생님 화면
![Figma](/assets/teacherView.png)
<br>

### [ERD](https://www.erdcloud.com/d/jzdAxd3ySZ8tXDa5n)

![ERD](/assets/ERD.png)
<br>

### [API 명세서](https://spiny-nylon-a25.notion.site/API-52047430168d4934ba76a379369aa596?pvs=4)

<details>
<summary>자세히 보기</summary>
<div markdown="1">

![api](/assets/api.png)
</div>
</details>
<br>

### 아키텍처

![image](/assets/architecture.png)
<br>

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
<br>

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
