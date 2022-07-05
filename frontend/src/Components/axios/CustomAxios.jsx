import axios from "axios";

const CustomAxios = axios.create({});
CustomAxios.interceptors.request.use(
   (request) => {
      request.headers["accessToken"] = localStorage.getItem("accessToken");
      request.headers["refresh-token"] = localStorage.getItem("refresh-token");
      return request;
   },
   (err) => {
      return Promise.reject(err);
   }
);
CustomAxios.interceptors.response.use(
   (response) => {
      return response;
   },
   (err) => {
      const status = err.response ? err.response.status : "null";
      if (status === 401) {
         axios
            .get("http://localhost:8080/token/refresh", {
               "refresh-token": localStorage.getItem("refreshToken"),
            })
            .then((response) => {
               localStorage.setItem("refresh-token", response.data[`refresh-token`]);
               localStorage.setItem("token", response.data.accessToken);
               console.log("new token resived");
            })
            .catch((err) => {});
      }
      return Promise.resolve(err);
   }
);

export default CustomAxios;
