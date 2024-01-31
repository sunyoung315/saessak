import { localAxios } from "./http";

const local = localAxios();

const url = "/oauth";


function kakaoLogin(sucess, fail){
    const config = {
        headers : {
            'Origin' : 'http://localhost:5173/'
        }
    }

    local.get(`${url}/kakao/login`).then(sucess).catch(fail);
}

export {
    kakaoLogin
}