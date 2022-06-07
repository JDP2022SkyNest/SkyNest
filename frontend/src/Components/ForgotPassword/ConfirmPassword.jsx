import React, { useState } from "react";
import Label from "../ReusableComponents/Label";
import { useNavigate } from "react-router-dom";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import { passwordRegEx } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";

const ConfirmPassword = () => {
   const [password, setPassword] = useState("");
   const [confPassword, setConfPassword] = useState("");
   const [showPassword, setShowPassword] = useState(false);
   const [errorMsg, setErrorMsg] = useState(false);
   const [successMsg, setSuccessMsg] = useState("");

   const navigate = useNavigate();

   const redirectToLoginPage = (delay) => {
      setTimeout(() => {
         navigate(ROUTES.LOGIN);
      }, delay);
   };

   const onSuccessfulValidation = password.match(passwordRegEx) && password === confPassword;

   const onFormSubmit = (e) => {
      e.preventDefault();
      if (onSuccessfulValidation) {
         setSuccessMsg("Password changed successfully");
         redirectToLoginPage(1500);
      } else {
         setErrorMsg("Passwords do not match");
      }
   };

   const passwordShowHide = () => {
      setShowPassword(!showPassword);
   };

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
            <button className={`mt-5 btn btn-dark btn-lg btn-block ${onSuccessfulValidation ? "" : "disabled"} `}>Reset my password</button>
         </form>
      </CenteredContainer>
   );
};

export default ConfirmPassword;
