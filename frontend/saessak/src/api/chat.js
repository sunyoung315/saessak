import { localAxios } from "./http";

const local = localAxios();

const url = "/chat";

const config = {
    headers : {
        "Authorization" : "Bearer " + sessionStorage.getItem("accessToken")
    }
} // 헤더에 accessToken 담아서 전송하기!!

// 1. (선생님) 나의 반 학부모 목록 조회 --> user에서 가져오기

// 2. (학부모) 나의 전체 아이 선생님 목록 조회 - parentId --> user에서 가져오기

// 3. (선생님) 내가 참여하고 있는 채팅방 목록 조회 - teacherId
function chatListTeacher(sucess, fail){
    local.get(`${url}/teacher/list`, config).then(sucess).catch(fail);
}

// 4. (학부모) 내가 참여하고 있는 채팅방 목록 조회 - parentId
function chatListParent(sucess, fail){
    local.get(`${url}/parent/list`, config).then(sucess).catch(fail);
}

// 5. (공통) 상세 채팅 내용 불러오기 - roomId
function detailChat(roomId, sucess, fail){
    local.get(`${url}/${roomId}`).then(sucess).catch(fail);
}


// I'm 티처 -> to Kid : 내가 누구? 토큰있음 애가 누구? 애목록에 키드아이디 있음 -> 티처토큰 + kid Id
// 6. (선생님) chat 버튼 눌러서 새로운 채팅 생성하기
function teacherNewChat(kidId, sucess, fail){
    local.post(`${url}/add/teacher/${kidId}`, null, config).then(sucess).catch(fail);
}

// I'm 마덜 -> to 티처 : 애가 누구? 애는 여러명이면 현재화면이랑 상관없이 다 뜸 -> 티처는 뜨니까 티처id -> 티처iD + 학부모토큰
// 6. (학부모) chat 버튼 눌러서 새로운 채팅 생성하기
function parentNewChat(teacherId, sucess, fail){
    local.post(`${url}/add/parent/${teacherId}`, null,  config).then(sucess).catch(fail);
}

// 7. (공통) 채팅방 진입 시 이전에 저장된 채팅 내역 불러오기
function loadChat(roomId, param, sucess, fail){
    local.post(`${url}/room/list/${roomId}`, JSON.stringify(param)).then(sucess).catch(fail);
}

// 8. (공통) 채팅방 진입 전 userId 받아오기
function isVaild(success, fail){
    local.get(`${url}/isVaild`, config).then(success).catch(fail);
}

// 9. (공통) 채팅방 퇴장 시간 기록하기
function disconnect(param, success, fail) {
    local.get(`${url}/disconnect`, {params : param}).then(success).catch(fail);
}


export {
    chatListParent,
    chatListTeacher,
    detailChat,
    teacherNewChat,
    parentNewChat,
    loadChat,
    isVaild,
    disconnect
}