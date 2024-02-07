import { localAxios } from "./http";

const local = localAxios();

const url = "/notice";

const config = {
    headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("accessToken")
    }
}

// noticeList 전부 불러오기
function noticeListAll(kidId, param, sucess, fail) {
    local.get(`${url}/all/${kidId}`, param).then(sucess).catch(fail);
}


export {
    noticeListAll
}