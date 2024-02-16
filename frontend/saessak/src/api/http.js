import axios from 'axios'

const { VITE_VUE_API_URL } = import.meta.env

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })

  instance.interceptors.response.use(
    function (response) {
      return response
    },
    async (error) => {
      const {
        config,
        response: { status }
      } = error

      if (status === 401) {
        const originalRequest = config
        const refreshToken = localStorage.getItem('refreshToken')

        if (refreshToken) {
          await instance
            .post('/oauth/refreshtoken', {
              refreshToken: localStorage.getItem('refreshToken')
            })
            .then((response) => {
              const newAccessToken = response.data.accessToken
              localStorage.setItem('accessToken', newAccessToken)

              return instance(originalRequest)
            })
        }
      }
    }
  )
  return instance
}

export { localAxios }
