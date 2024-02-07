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

async function userDelete(userId, success, fail){
    await local.delete(`${url}/${userId}`).then(success).catch(fail);
}

export {
    kakaoLogin,
    userDelete
}