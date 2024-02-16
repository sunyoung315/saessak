import { localAxios } from "./http";

const local = localAxios();

const url = "/oauth";

const config = {
    headers : {
        "Authorization" : "Bearer " + localStorage.getItem("accessToken")
    }
} // 헤더에 accessToken 담아서 전송하기!!

function userLogout(success, fail) {
    local.get(`${url}/logout`, config).then(success).catch(fail);
}

async function userDelete(userId, success, fail){
    await local.delete(`${url}/${userId}`).then(success).catch(fail);
}

export {
    userLogout,
    userDelete
}