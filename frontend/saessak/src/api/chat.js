import { localAxios } from "./http";

const local = localAxios();

const url = "/chat";

// 1. (선생님) 나의 반 학부모 목록 조회 --> user에서 가져오기

// 2. (학부모) 나의 전체 아이 선생님 목록 조회 - parentId --> user에서 가져오기

// 3. (선생님) 내가 참여하고 있는 채팅방 목록 조회 - teacherId
function chatListTeacher(parentId, sucess, fail){
    local.get(`${url}/parent/list/${parentId}`).then(sucess).catch(fail);
}

// 4. (학부모) 내가 참여하고 있는 채팅방 목록 조회 - parentId
function chatListParent(teacherId, sucess, fail){
    local.get(`${url}/teacher/list/${teacherId}`).then(sucess).catch(fail);
}

// 5. (공통) 상세 채팅 내용 불러오기 - roomId
function detailChat(roomId, sucess, fail){
    local.get(`${url}/${roomId}`).then(sucess).catch(fail);
}

// 6. (공통) chat 버튼 눌러서 새로운 채팅 생성하기
function addNewChat(teacherId, kidId, sucess, fail){
    local.post(`${url}/add/${teacherId}/${kidId}`).then(sucess).catch(fail);
}


export {
    chatListParent,
    chatListTeacher,
    detailChat,
    addNewChat
}