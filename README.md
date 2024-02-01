# ⭐️ 새싹일기 

> SSAFY 10기 2학기 공통 프로젝트 - 새싹일기 <br/>
> https://i10a706.p.ssafy.io/

- 주제: 우리 아이 어린이집 생활 공유 서비스
- 기간: 2024.01.03 ~ 2024.02.16

<br/>

## 👐 프로젝트 산출물 👐
- [Figma](https://www.figma.com/file/ACANoSkPAK37VNyMIMjHNl/%EC%83%88%EC%8B%B9%EC%9D%BC%EA%B8%B0?type=design&node-id=0-1&mode=design&t=zhn7Jt6eixMXWhoA-0)   
- ERD

- [API 명세서](https://spiny-nylon-a25.notion.site/API-52047430168d4934ba76a379369aa596?pvs=4)

- 아키텍처
![image](/uploads/5c3dc1046b7778ac12341896a922fd2e/image.png)

- [포팅 매뉴얼](https://spiny-nylon-a25.notion.site/607d2233ca3d498caaad1617cc111ed3?pvs=4)   

<br/>

## 👐 진행 상황 👐
###  **프론트엔드**
#### - 이민주
- 카카오 로그인 백엔드와 연결
- WebSocket, Stomp를 사용한 기본 채팅 기능 구현
- 채팅 기능에 JWT 토큰을 통한 인증 추가(프론트)
- JWT 토큰을 사용한 채팅 송수신자 구분 기능 구현
#### - 김선영
  1. 출석부
    - 선생님ver 화면 구성 및 기능 구현
        - 데이트 피커로 해당 월 주차 계산하여 주차별 테이블
        - 당일은 등/하원 버튼 → 클릭 시 등록된 학부모에게 알림 → 등원 시간으로 버튼 변경
        - 데이터 정보에 따라 시간/미입력/결석 표시
    - 학부모ver 화면 구성 및 기능 구현
        - vue-cal 라이브러리
        - 달력으로 아이 등/하원 시간 조회
  2. 알림장
    - 선생님ver 리스트, 상세조회, 작성 화면 구성 및 기능 구현
        - 반 아이들 조회 → 알림장 리스트(이름, 프로필 사진)
        - 한 아이의 알림장 조회 시 가장 최근 알림장 상세 조회 + 데이트피커로 다른 날짜의 알림장 조회
        - 알림장 내용 및 건강 기록 작성
    - 학부모ver 리스트, 상세조회 화면 구성 및 기능 구현
        - 날짜별로 리스트 조회
        - 알림장 상세조회 + 데이트피커로 다른 날짜의 알림장 조회
        - 성장 기록 확인 차트 라이브러리
        - 요약레포트(OpenAI API) 기능 구현 중(생성 시간 문제 해결 필요)…
  3. 식단표
    - 주차별 식단표 조회 레이아웃 구성 중
#### - 김종인
  1. 앨범
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
  2. 동의서
     - 전자서명 기능 구현 (현재 백과 연동되지 않고 프론트에서만 적용)
     - 귀가 동의서 화면 구성 및 더미 데이터를 적용한 기능 구현
     - 식품 알레르기 동의서 화면 구성 및 더미 데이터를 적용한 기능 구현
### **백엔드**
#### - 김하은
- 인프라 설정 완료
- WebSocket, Stomp를 사용한 채팅 서버 구현
- 채팅 기능에 Redis 캐싱 구현 중
- 공지사항 API 작성
#### - 이민우
- deepface를 이용한 얼굴 사진 분류 api작성
- flask 서버 jwt 를 이용한 권한 관리
- 엘범 api 작성
- 게시판 api 작성
- 엘범, 게시판 프론트와 연동중
#### - 윤선아
  1. FCM
      - 등하원 알림
      - 식품 알러지 알림 ( scheduling )
      - 대리인 귀가 알림
  2. 소셜로그인
      - OAuth2 Kakao 로그인 기능 구현
      - BASE64 암호코드를 활용한 회원가입 (학부모는 kid, 선생님은 classroomid)
      - accessToken, refreshToken JWT로 암호화
      - refreshToken는 Redis 저장
      - accessToken으로 Authentication 인증 관련 서비스 구현
      - SpringSecurity, JwtAuthenticationFilter으로 401 Error 처리
  3. JPA CRUD 구현
      - 의뢰서, 식단표, 출석부, 알람, 설정, 유저
  4. 식단메뉴, 유저프로필에 대한 사진 AWS S3 upload
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
        </td>
        <td>
        1. 아이디어 주제 기획 및 선정<br>
        2. FE 알림장 기능 구현<br>
        </td>
        <td>
        1. Vue Layout 설계<br>
        2. FE 앨범 기능 구현<br>
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
        </td>
        <td>
        1. BE 알림 기능 구현<br>
        2. BE 전자 서명 구현<br>
        </td>
        <td>
        1. BE 사진 필터링 AI 기술 적용<br>
        2. BE 생성형 API 기술 적용<br>
        </td>
    </tr>
</table>

<br/>
