import { localAxios } from './http'

const local = localAxios()

const url = '/alarm'

const config = {
  headers: {
    Authorization: 'Bearer ' + sessionStorage.getItem('accessToken')
  }
}

function alarmListOfParent(kidId, success, fail) {
  console.log('axios: parent')
  local.get(`${url}/kid/${kidId}`, config).then(success).catch(fail)
}

function alarmListOfTeacher(success, fail) {
  console.log('axios: teacher')
  local.get(`${url}/classroom`, config).then(success).catch(fail)
}

export { alarmListOfParent, alarmListOfTeacher }
