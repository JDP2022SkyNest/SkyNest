import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { pwSuggestion, passwordRegEx, redirectTo } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import AxiosInstance from "../axios/AxiosInstance";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import Label from "../ReusableComponents/Label";
import "./SignUp.css";
import LoadingButton from "../Loader/LoadingButton";
import PasswordRequirements from "../ReusableComponents/PasswordRequirements";

const SignUp = () => {
   const [name, setName] = useState("");
   const [surname, setSurname] = useState("");
   const [email, setEmail] = useState("");
   const [phoneNumber, setPhoneNumber] = useState("");
   const [address, setAddress] = useState("");
   const [uPassword, setPassword] = useState("");
   const [confPassword, setConfPassword] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [successfulRegister, setSuccessfulRegister] = useState("");
   const [buttonText, setButtonText] = useState("REGISTER");
   const [showPassword, setShowPassword] = useState(false);
   const [loading, setLoading] = useState(false);

   const navigate = useNavigate();

   const userRegistration = async () => {
      try {
         await AxiosInstance.post("/users/register", {
            email,
            password: uPassword,
            name,
            surname,
            phoneNumber,
            address,
         });
         setErrorMsg("");
         setSuccessfulRegister("Please confirm your email");
         setButtonText("SUCCESSFUL");
         redirectTo(navigate, ROUTES.LOGIN, 2000);
      } catch (err) {
         if (err.response.status === 409) {
            setErrorMsg("Email aready exists");
         } else if (err.response.status === 500) {
            setErrorMsg("Internal Server Error");
         } else if (err.response.status === 0) {
            setErrorMsg("Server Timeout");
         } else {
            setErrorMsg(err.response.data.messages);
         }
      }
      setLoading(false);
   };

   const isSuccessfulValidation = uPassword.match(passwordRegEx) && uPassword === confPassword;

   const handleSubmit = async (e) => {
      e.preventDefault();
      if (isSuccessfulValidation) {
         setLoading(true);
         await userRegistration();
      } else {
         setErrorMsg("Requirements not met");
      }
   };

   const passwordShowHide = () => {
      setShowPassword(!showPassword);
   };

   useEffect(() => {
      setErrorMsg("");
   }, [email]);

   return (
      <CenteredContainer>
         <form onSubmit={handleSubmit}>
            <h2 className="text-center">SKY-NEST</h2>
            <p className="mb-4 p-0 text-center text-secondary">Create your account</p>
            <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
            <p className={successfulRegister ? "alert alert-success text-success text-center" : "d-none"}>{successfulRegister}</p>
            <fieldset disabled={loading ? true : false}>
               <div className="row">
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <Label id="firstNameInput">First Name</Label>
                        <input
                           type="name"
                           name="firstName"
                           id="firstNameInput"
                           value={name}
                           onChange={(e) => setName(e.target.value)}
                           className="form-control"
                           required
                           autoComplete="off"
                        />
                     </div>
                  </div>
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <Label id="lastNameInput">Last Name</Label>
                        <input
                           type="name"
                           name="lastName"
                           value={surname}
                           onChange={(e) => setSurname(e.target.value)}
                           id="lastNameInput"
                           className="form-control"
                           required
                           autoComplete="off"
                        />
                     </div>
                  </div>
               </div>
               <div className="form-outline mb-1">
                  <Label id="emailInput">Email adress</Label>
                  <input
                     type="email"
                     name="email"
                     value={email}
                     onChange={(e) => setEmail(e.target.value)}
                     id="emailInput"
                     className="form-control"
                     required
                     autoComplete="off"
                  />
               </div>
               <div className="form-outline mb-1">
                  <Label id="phoneInput">Phone number</Label>
                  <input
                     type="number"
                     name="phoneNumber"
                     value={phoneNumber}
                     onChange={(e) => setPhoneNumber(e.target.value)}
                     id="phoneInput"
                     className="form-control no-arrow"
                     required
                     autoComplete="off"
                  />
               </div>
               <div className="form-outline mb-1">
                  <Label id="adressInput">Home adress</Label>
                  <input
                     type="text"
                     name="adress"
                     value={address}
                     onChange={(e) => setAddress(e.target.value)}
                     id="adressInput"
                     className="form-control"
                     required
                     autoComplete="off"
                  />
               </div>
               <div className="row">
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <Label id="passwordInput">
                           Password
                           <div className="suggestPwPosition btn-link" onClick={() => pwSuggestion(10, setPassword, setConfPassword)}>
                              Suggest PW?
                           </div>
                        </Label>
                        <div className="input-group">
                           <input
                              type={showPassword ? "text" : "password"}
                              name="password"
                              value={uPassword}
                              onChange={(e) => setPassword(e.target.value)}
                              id="passwordInput"
                              className="form-control border-right-0"
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
                        <Label id="confPasswordInput">Confirm password</Label>
                        <input
                           type={showPassword ? "text" : "password"}
                           name="confPassword"
                           value={confPassword}
                           onChange={(e) => setConfPassword(e.target.value)}
                           id="confPasswordInput"
                           className="form-control"
                           required
                           autoComplete="off"
                        />
                     </div>
                  </div>
               </div>
            </fieldset>
            {PasswordRequirements(uPassword, confPassword)}
            <div className="my-4">
               {!loading ? (
                  <button
                     className={isSuccessfulValidation ? "btn btn-dark btn-lg btn-block" : "btn btn-secondary btn-lg btn-block"}
                     disabled={isSuccessfulValidation ? false : true}
                  >
                     {buttonText}
                  </button>
               ) : (
                  <LoadingButton />
               )}
            </div>
            <div className="mt-4 text-center">
               <p className="m-0">Already have an account? </p>
               <Link to={ROUTES.LOGIN} className="m-0 btn btn-link">
                  Login
               </Link>
            </div>
         </form>
      </CenteredContainer>
   );
};

export default SignUp;
