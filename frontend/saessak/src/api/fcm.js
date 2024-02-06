import { localAxios } from "./http";

const local = localAxios();

const url = "/fcm/token";

const config = {
    headers : {
        "Authorization" : "Bearer " + sessionStorage.getItem("accessToken")
    }
} 

function saveToken(param, success, fail){
    local.post(`${url}`, JSON.stringify(param), config).then(success).catch(fail);
}

function deleteToken(success, fail){
    local.delete(`${url}`, config).then(success).catch(fail);
}

export {
    saveToken,
    deleteToken
}