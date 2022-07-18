import AxiosInstance from "../axios/AxiosInstance";

AxiosInstance.interceptors.response.use(
   (response) =>
      function (error) {
         const originalRequest = error.config;

         if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            const response = AxiosInstance.get("/token/refresh", {
               headers: {
                  "refresh-token": refreshToken,
               },
            });
            let { headers } = response;
            let token = headers.authorization;
            setAccessToken(token);
            localStorage.setItem("accessToken", token);
         }
         return Promise.reject(error);
      }
);
