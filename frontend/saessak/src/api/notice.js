import { localAxios } from './http';

const local = localAxios();

const url = '/notice';

const config = {
	headers: {
		Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
	},
};

// noticeList 전부 불러오기
const noticeListParentAll = async (kidId, pageNo, sucess, fail) => {
	await local
		.get(`${url}/all/parent/${kidId}?pageNo=${pageNo}`)
		.then(sucess)
		.catch(fail);
};

const noticeListTeacherAll = async (param, sucess, fail) => {
	const config = {
		headers: {
			Authorization: 'Bearer ' + localStorage.getItem('accessToken'),
		},
		params: {
			pageNo: param,
		},
	};
	await local.get(`${url}/all/teacher`, config).then(sucess).catch(fail);
};

const parentFix = async (param, sucess, fail) => {
	await local
		.post(`${url}/fixed/parent/add`, JSON.stringify(param))
		.then(sucess)
		.catch(fail);
};

const parentNotFix = async (param, success, fail) => {
	await local
		.delete(`${url}/fixed/parent/delete`, { data: param })
		.then(success)
		.catch(fail);
};

const teacherFix = async (param, sucess, fail) => {
	await local
		.post(`${url}/fixed/teacher/add?noticeId=${param}`, null, config)
		.then(sucess)
		.catch(fail);
};

const teacherNotFix = async (param, success, fail) => {
	await local
		.delete(`${url}/fixed/teacher/delete?noticeId=${param}`, config)
		.then(success)
		.catch(fail);
};

function noticeDetail(noticeId, sucess, fail) {
	local.get(`${url}/detail/${noticeId}`, config).then(sucess).catch(fail);
}

export {
	noticeListParentAll,
	noticeListTeacherAll,
	noticeDetail,
	parentNotFix,
	parentFix,
	teacherNotFix,
	teacherFix,
};
