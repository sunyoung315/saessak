import { localAxios } from "./http";

const local = localAxios();

const url = "/user";

const config = {
    headers : {
        "Authorization" : "Bearer " + sessionStorage.getItem("accessToken")
    }
} // 헤더에 accessToken 담아서 전송하기!!

// (선생님) 로그인
function teacherLogin(sucess, fail){
    local.post(`${url}/teacher/login`).then(sucess).catch(fail);
}


// (선생님) 나의 반 아이들 조회하기
function getClassKids(sucess, fail){
    local.get(`${url}/kid/list`, config).then(sucess).catch(fail);
}

// (학부모) 나의 아이 담당 선생님 조회하기
function getMyTeacher(sucess, fail){
    local.get(`${url}/teacher`, config).then(sucess).catch(fail);
}

// (학부모) 새로운 아이 코드 등록하기
function kidRegister(param, success, fail){
    local.post(`${url}/kid/mapping`, JSON.stringify(param), config).then(success).catch(fail);
}

// (학부모) 전체 아이 리스트 조회하기
function getkidList(success, fail){
    local.get(`${url}/parent/kidlist`, config).then(success).catch(fail);
}

export {
    getClassKids,
    getMyTeacher,
    teacherLogin,
    kidRegister,
    getkidList
};