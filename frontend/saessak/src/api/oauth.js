import { localAxios } from "./http";

const local = localAxios();

const url = "/oauth";


function kakaoLogin(sucess, fail){
    local.get(`${url}/kakao/login`).then(sucess).catch(fail);
}

export {
    kakaoLogin
}