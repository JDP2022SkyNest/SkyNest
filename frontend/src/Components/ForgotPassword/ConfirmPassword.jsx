import React, { useEffect, useState } from "react";
import Label from "../ReusableComponents/Label";
import { useNavigate } from "react-router-dom";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import { passwordRegEx } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import { useSearchParams } from "react-router-dom";
import AxiosInstance from "../axios/AxiosInstance";

const ConfirmPassword = () => {
   const [password, setPassword] = useState("");
   const [confPassword, setConfPassword] = useState("");
   const [showPassword, setShowPassword] = useState(false);
   const [errorMsg, setErrorMsg] = useState(false);
   const [successMsg, setSuccessMsg] = useState("");
   const [searchParams, setSearchParams] = useSearchParams();

   const pwToken = searchParams.get("token");

   const navigate = useNavigate();
   const isSuccessfulValidation = password.match(passwordRegEx) && password === confPassword;

   const onPwSubmit = async () => {
      try {
         await AxiosInstance.put("/users/password-reset", {
            token: pwToken,
            password,
         });
         setSuccessMsg("Password changed successfully");
         redirectTo(navigate, ROUTES.LOGIN, 1500);
      } catch (err) {
         setErrorMsg("Failed");
         console.log(err);
      }
   };

   const onFormSubmit = async (e) => {
      e.preventDefault();
      if (isSuccessfulValidation) {
         console.log(password, pwToken);
         await onPwSubmit();
      } else {
         setSearchParams({ token: pwToken });
         setErrorMsg("Passwords do not match");
      }
   };

   const passwordShowHide = () => {
      setShowPassword(!showPassword);
   };

   useEffect(() => {
      setErrorMsg("");
   }, [password, confPassword]);

   return (
      <CenteredContainer>
         <h4 className="text-center mb-5">Enter your new password</h4>
         <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
         <p className={successMsg ? "alert alert-success text-success text-center" : "d-none"}>{successMsg}</p>
         <form onSubmit={onFormSubmit}>
            <div className="row">
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label>Password</Label>
                     <div className="input-group">
                        <input
                           type={showPassword ? "text" : "password"}
                           name="password"
                           value={password}
                           onChange={(e) => setPassword(e.target.value)}
                           id="passwordInput"
                           className="form-control form-control border-right-0"
                           required
                           autoComplete="off"
                        />
                        <div className="input-group-prepend">
                           <span onClick={passwordShowHide} className="input-group-text bg-white rounded-right border-left-0">
                              <i className={showPassword ? "fa-solid fa-eye" : "fa fa-eye"}></i>
                           </span>
                        </div>
                     </div>
                  </div>
               </div>
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label>Confirm password</Label>
                     <input
                        type={showPassword ? "text" : "password"}
                        name="confPassword"
                        value={confPassword}
                        onChange={(e) => setConfPassword(e.target.value)}
                        id="confPasswordInput"
                        className="form-control form-control"
                        required
                        autoComplete="off"
                     />
                  </div>
               </div>
            </div>
            <button
               className={`mt-5 btn btn-dark btn-lg btn-block ${isSuccessfulValidation ? "" : "disabled"}`}
               disabled={isSuccessfulValidation ? false : true}
            >
               Reset my password
            </button>
         </form>
      </CenteredContainer>
   );
};

export default ConfirmPassword;
