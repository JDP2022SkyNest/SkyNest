import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";
import logoImage from "./assets/logoblackandwhite.svg";
import AxiosInstance from "../axios/AxiosInstance";
import ROUTES from "../Routes/ROUTES";
import CenteredContainer from "../ReusableComponents/CenteredContainer";

const Login = ({ setAccessToken }) => {
   const [email, setEmail] = useState("");
   const [password, setPassword] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [showPassword, setShowPassword] = useState(false);
   const [loading, setLoading] = useState(false);
   const [successfulLogin, setSuccessfulLogin] = useState("");

   const emailRef = useRef();
   const passwordRef = useRef();

   const navigate = useNavigate();

   const redirectToHomePage = (delay) => {
      setTimeout(() => {
         navigate(ROUTES.HOME);
      }, delay);
   };

   const getUserToken = async () => {
      try {
         let reposnse = await AxiosInstance.post("/login", { email, password });
         let { data } = reposnse;
         if (data?.accessToken) {
            setAccessToken(data.accessToken);
            localStorage.setItem("accessToken", data.accessToken);
            setSuccessfulLogin("Login Successful, redirecting.");
            redirectToHomePage(1000);
         } else {
            setErrorMsg("Internal error, please try again");
         }
      } catch ({ response }) {
         if (response.status === 400) {
            setErrorMsg("Incorrect username or password");
         } else if (response.status === 0) {
            setErrorMsg("Server Timeout");
         }
         setLoading(false);
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
      disableEnableInputs(true);
      setLoading(true);
      await getUserToken();
      disableEnableInputs(false);
   };

   const onEmailChange = (e) => {
      setEmail(e.target.value);
   };

   const onPasswordChange = (e) => {
      setPassword(e.target.value);
   };

   const passwordShowHide = () => {
      setShowPassword(!showPassword);
   };

   const disableEnableInputs = (value) => {
      emailRef.current.disabled = value;
      passwordRef.current.disabled = value;
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
            <div className="form-outline mb-4">
               <label className="form-label" htmlFor="emailInput">
                  Email address
               </label>
               <input
                  type="email"
                  onChange={onEmailChange}
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
                     onChange={onPasswordChange}
                     value={password}
                     ref={passwordRef}
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
            </div>
            {loading ? (
               <div className="pt-1">
                  <button className="btn btn-dark btn-lg btn-block d-flex align-items-center justify-content-center" disabled>
                     <span className="spinner-border spinner-border-md"></span>
                  </button>
               </div>
            ) : (
               <div className="pt-1 mb-4">
                  <button className="btn btn-dark btn-lg btn-block">Login</button>
               </div>
            )}
            <div className="mt-5 text-center">
               <p className="m-0">Don't have an account? </p>
               <Link to={ROUTES.SIGNUP} href="#!" className="m-0 btn btn-link">
                  Register here
               </Link>
            </div>
         </form>
      </CenteredContainer>
   );
};

export default Login;
