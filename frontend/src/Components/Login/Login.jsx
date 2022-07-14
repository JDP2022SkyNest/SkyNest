import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import { redirectTo, emailVerification, getRefreshToken } from "../ReusableComponents/ReusableFunctions";
import "./Login.css";
import logoImage from "./assets/logoblackandwhite.svg";
import AxiosInstance from "../axios/AxiosInstance";
import ROUTES from "../Routes/ROUTES";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import LoadingButton from "../Loader/LoadingButton";
import { useContext } from "react";
import GlobalContext from "../context/GlobalContext";

const Login = () => {
   const [email, setEmail] = useState("");
   const [password, setPassword] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [showPassword, setShowPassword] = useState(false);
   const [loading, setLoading] = useState(false);
   const [successMsg, setSuccessMsg] = useState("");
   const [forgotPassword, setForgotPassword] = useState(false);
   const [searchParams, setSearchParams] = useSearchParams();
   const [infoMsg, setInfoMsg] = useState("");
   const [resendEmail, setResendEmail] = useState(false);

   const { setAccessToken } = useContext(GlobalContext);

   const emailRef = useRef();
   const navigate = useNavigate();
   const token = searchParams.get("token");

   const getUserToken = async () => {
      try {
         let reposnse = await AxiosInstance.post("/public/login", { email, password });
         let { headers } = reposnse;
         let token = headers.authorization;
         let refreshToken = headers[`refresh-token`];
         if (headers?.authorization) {
            setAccessToken(token);
            localStorage.setItem("accessToken", token);
            localStorage.setItem("refresh-token", refreshToken);
            setSuccessMsg("Login Successful");
            redirectTo(navigate, ROUTES.HOME, 1000);
            setErrorMsg("");
         } else {
            setErrorMsg("Internal error, please try again");
            setLoading(false);
         }
      } catch (err) {
         if (err.response.status === 0) {
            setErrorMsg("Server Timeout");
         } else if (err.response.status === 404) {
            setErrorMsg("User doesn't exist");
         } else if (err.response.status === 401) {
            setErrorMsg("Wrong password");
         } else {
            setErrorMsg(err.response.data.messages);
            console.log(err.response.status);
         }
         setLoading(false);
         setForgotPassword(true);
         setSuccessMsg("");
      }
   };

   AxiosInstance.interceptors.request.use(
      (config) => {
         // eslint-disable-next-line
         const token = window.localStorage.getItem("accessToken", token);
         if (token) {
            config.headers["Authorization"] = "Bearer " + token;
         }
         // config.headers['Content-Type'] = 'application/json';
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
            const refreshToken = getRefreshToken();
            return AxiosInstance.get("/token/refresh", {
               headers: {
                  "refresh-token": refreshToken,
               },
            }).then((res) => {
               if (res.status === 201) {
                  window.localStorage.setAccessToken(res.data);
                  AxiosInstance.defaults.headers.common["Authorization"] = "Bearer " + getRefreshToken();
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

   useEffect(() => {
      emailRef.current.focus();
   }, []);

   useEffect(() => {
      setErrorMsg("");
   }, [email, password]);

   const onFormSubmit = async (e) => {
      e.preventDefault();
      setLoading(true);
      await getUserToken();
   };

   const passwordShowHide = () => {
      setShowPassword(!showPassword);
   };

   return (
      <CenteredContainer>
         <form onSubmit={onFormSubmit}>
            <div className="d-flex justify-content-center m-0">
               <img src={logoImage} alt="LOGO" className="little-image-logo" />
            </div>
            <h1 className="mt-2 text-center">SKY-NEST</h1>
            <p className="mb-5 p-0 text-center text-secondary">Sign into your account</p>
            <p className={infoMsg ? "alert alert-info text-info text-center" : "d-none"}>{infoMsg}</p>
            <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
            <p className={successMsg ? "alert alert-success text-success text-center" : "d-none"}>{successMsg}</p>
            <fieldset disabled={loading}>
               <div className="form-outline mb-4">
                  <label className="form-label" htmlFor="emailInput">
                     Email address
                  </label>
                  <input
                     type="email"
                     onChange={(e) => setEmail(e.target.value)}
                     value={email}
                     ref={emailRef}
                     id="emailInput"
                     className={`form-control form-control-lg ${errorMsg ? "border-danger" : null}`}
                     required
                     autoComplete="off"
                  />
                  <small className={resendEmail ? "p-0" : "d-none"}>
                     <Link to={ROUTES.RESEND}>Resend Email?</Link>
                  </small>
               </div>
               <div className="form-outline mb-4">
                  <label className="form-label" htmlFor="passwordInput">
                     Password
                  </label>
                  <div className="input-group input-group-lg ">
                     <input
                        type={showPassword ? "text" : "password"}
                        onChange={(e) => setPassword(e.target.value)}
                        value={password}
                        id="passwordInput"
                        className={`form-control form-control-lg ${errorMsg ? "border-danger" : "border-right-0"}`}
                        required
                     />
                     <div className="input-group-prepend">
                        <span onClick={passwordShowHide} className="input-group-text bg-white rounded-right border-left-0">
                           <i className={showPassword ? "fa-solid fa-eye" : "fa fa-eye"}></i>
                        </span>
                     </div>
                  </div>
                  <small className={forgotPassword ? "p-0" : "d-none"}>
                     <Link to={ROUTES.FORGOTPW}>Forgot Password?</Link>
                  </small>
               </div>
            </fieldset>
            {loading ? (
               <LoadingButton />
            ) : (
               <div className="pt-1 mb-4">
                  <button className="btn btn-dark btn-lg btn-block">Login</button>
               </div>
            )}
            <div className="mt-5 text-center">
               <p className="m-0">Don't have an account? </p>
               <Link to={ROUTES.SIGNUP} className="m-0 btn btn-link">
                  Register here
               </Link>
            </div>
         </form>
      </CenteredContainer>
   );
};

export default Login;
