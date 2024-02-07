import { localAxios } from "./http";

const local = localAxios();

const url = "/notice";

const config = {
    headers: {
        "Authorization": "Bearer " + sessionStorage.getItem("accessToken")
    }
}

// noticeList 전부 불러오기
function noticeListParentAll(kidId, param, sucess, fail) {
    local.get(`${url}/all/parent/${kidId}`, param).then(sucess).catch(fail);
}

function noticeListTeacherAll(param, sucess, fail) {
    local.get(`${url}/all/teacher`, param, config).then(sucess).catch(fail);
}

export {
    noticeListParentAll,
    noticeListTeacherAll
}