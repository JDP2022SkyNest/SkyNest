import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import "./Login.css";
import logoImage from "./assets/logoblackandwhite.svg";
import AxiosInstance from "../axios/AxiosInstance";
import ROUTES from "../Routes/ROUTES";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import LoadingButton from "../Loader/LoadingButton";

const Login = ({ setAccessToken }) => {
   const [email, setEmail] = useState("");
   const [password, setPassword] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [showPassword, setShowPassword] = useState(false);
   const [loading, setLoading] = useState(false);
   const [successfulLogin, setSuccessfulLogin] = useState("");
   const [forgotPassword, setForgotPassword] = useState(false);

   const emailRef = useRef();
   const navigate = useNavigate();

   const getUserToken = async () => {
      try {
         let reposnse = await AxiosInstance.post("/users/login", { email, password });
         let { headers } = reposnse;
         let token = headers.authorization;
         if (headers?.authorization) {
            setAccessToken(token);
            localStorage.setItem("accessToken", token);
            setSuccessfulLogin("Login Successful.");
            redirectTo(navigate, ROUTES.HOME, 1000);
            setErrorMsg("");
         } else {
            setErrorMsg("Internal error, please try again");
            setLoading(false);
         }
      } catch (err) {
         if (err.response.status === 403) {
            setErrorMsg("Incorrect username or password");
         } else if (err.response.status === 0) {
            setErrorMsg("Server Timeout");
         } else if (err.response.status === 400) {
            setErrorMsg("User doesn't exist");
         } else if (err.response.status === 401) {
            setErrorMsg("Email not verified");
         } else if (err.response.status === 417) {
            setErrorMsg("Wrong password");
         } else {
            setErrorMsg("Unknown Error");
         }
         setLoading(false);
         setForgotPassword(true);
      }
   };

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
            <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
            <p className={successfulLogin ? "alert alert-success text-success text-center" : "d-none"}>{successfulLogin}</p>
            <fieldset disabled={loading ? true : false}>
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
