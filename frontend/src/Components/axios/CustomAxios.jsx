import AxiosInstance from "./AxiosInstance";
import { getRefreshToken } from "../ReusableComponents/ReusableFunctions";

AxiosInstance.interceptors.request.use(
   (config) => {
      const token = window.localStorage.getItem("accessToken", token);
      if (token) {
         config.headers["Authorization"] = "Bearer " + token;
      }
      return config;
   },
   (error) => {
      Promise.reject(error);
   }
);

//Add a response interceptor

AxiosInstance.interceptors.response.use(
   (response) => {
      return response;
   },
   function (error) {
      const originalRequest = error.config;

      if (error.response.status === 401 && !originalRequest._retry) {
         originalRequest._retry = true;
         return AxiosInstance.get("/token/refresh", {
            headers: {
               "refresh-token": getRefreshToken(),
            },
         }).then((res) => {
            if (res.status === 201) {
               window.localStorage.setAccessToken(response.headers[`refresh-token`]);
               AxiosInstance.defaults.headers.common["Authorization"] = "Bearer " + token;
               return AxiosInstance(originalRequest);
            }
         });
      }
      return Promise.reject(error);
   }
);
useEffect(() => {
   if (token) {
      emailVerification(token, setSuccessMsg, setErrorMsg, setInfoMsg, setSearchParams, setResendEmail);
   }
   // eslint-disable-next-line
}, [token]);
