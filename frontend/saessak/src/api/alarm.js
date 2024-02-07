import { localAxios } from './http'

const local = localAxios()

const url = '/alarm'

const config = {
  headers: {
    Authorization: 'Bearer ' + sessionStorage.getItem('accessToken')
  }
}

function alarmListOfParent(kidId, success, fail) {
  local.get(`${url}/kid/${kidId}`, config).then(success).catch(fail)
}

function alarmListOfTeacher(success, fail) {
  local.get(`${url}/classroom`, config).then(success).catch(fail)
}

function deleteAlarm(alarmId, success, fail) {
  local.delete(`${url}/${alarmId}`, config).then(success).catch(fail)
}

function deleteAllofParent(kidId, success, fail) {
  local.delete(`${url}/kid/${kidId}`, config).then(success).catch(fail)
}

function deleteAllofTeacher(success, fail) {
  local.delete(`${url}/classroom`, config).then(success).catch(fail)
}

export { alarmListOfParent, alarmListOfTeacher, deleteAlarm, deleteAllofParent, deleteAllofTeacher }
