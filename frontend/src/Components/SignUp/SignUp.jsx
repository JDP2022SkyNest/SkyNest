import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { pwSuggestion, RegEx } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import AxiosInstance from "../axios/AxiosInstance";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import Label from "../ReusableComponents/Label";
import "./SignUp.css";

const SignUp = () => {
   const [name, setName] = useState("");
   const [surname, setSurname] = useState("");
   const [email, setEmail] = useState("");
   const [phoneNumber, setPhoneNumber] = useState("");
   const [address, setAddress] = useState("");
   const [uPassword, setPassword] = useState("");
   const [confpassword, setConfpassword] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [successfulRegister, setSuccessfulRegister] = useState("");
   const [buttonText, setButtonText] = useState("REGISTER");
   const [showPassword, setShowPassword] = useState(false);
   const [loading, setLoading] = useState(false);

   const navigate = useNavigate();

   const redirectToLoginPage = (delay) => {
      setTimeout(() => {
         navigate(ROUTES.LOGIN);
      }, delay);
   };

   const allInputs = document.querySelectorAll("input");
   const inputsDisabled = (value) => [
      allInputs.forEach((el) => {
         el.disabled = value;
      }),
   ];

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
         setSuccessfulRegister("Registration Successful");
         setButtonText("SUCCESSFUL");
         redirectToLoginPage(2000);
      } catch ({ response }) {
         if (response.status === 409) {
            setErrorMsg("Email aready exists");
         } else if (response.status === 500) {
            setErrorMsg("Internal Server Error");
         } else if (response.status === 0) {
            setErrorMsg("Server currently offline");
         } else {
            setErrorMsg(response.data.messages);
         }
      }
      setLoading(false);
   };

   const handleSubmit = async (e) => {
      e.preventDefault();
      inputsDisabled(true);
      if (uPassword.match(RegEx) && uPassword === confpassword) {
         setLoading(true);
         await userRegistration();
      } else {
         setErrorMsg("Requirements not met");
      }
      inputsDisabled(false);
   };

   const onSuccessfulValidation = () => {
      if (uPassword.match(RegEx) && uPassword === confpassword) {
         return true;
      }
      return false;
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
            <div className="row">
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label>First Name</Label>
                     <input
                        type="name"
                        name="firstName"
                        id="firstNameInput"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="form-control form-control"
                        required
                        autoComplete="off"
                     />
                  </div>
               </div>
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label>Last Name</Label>
                     <input
                        type="name"
                        name="lastName"
                        value={surname}
                        onChange={(e) => setSurname(e.target.value)}
                        id="lastNameInput"
                        className="form-control form-control"
                        required
                        autoComplete="off"
                     />
                  </div>
               </div>
            </div>
            <div className="form-outline mb-1">
               <Label>Email adress</Label>
               <input
                  type="email"
                  name="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  id="emailInput"
                  className="form-control form-control"
                  required
                  autoComplete="off"
               />
            </div>
            <div className="form-outline mb-1">
               <Label>Phone number</Label>
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
               <Label>Home adress</Label>
               <input
                  type="text"
                  name="adress"
                  value={address}
                  onChange={(e) => setAddress(e.target.value)}
                  id="adressInput"
                  className="form-control form-control"
                  required
                  autoComplete="off"
               />
            </div>
            <div className="row">
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label>
                        Password
                        <div className="suggestPwPosition btn-link" onClick={() => pwSuggestion(10, setPassword, setConfpassword)}>
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
                        value={confpassword}
                        onChange={(e) => setConfpassword(e.target.value)}
                        id="confPasswordInput"
                        className="form-control form-control"
                        required
                        autoComplete="off"
                     />
                  </div>
               </div>
            </div>
            <small>
               Password requirements:
               <ul className="mt-2 bt-2 text-danger unordered-list-padding">
                  <li className={uPassword.match(/([A-Z])/) ? "text-success" : ""}>Uppercase Letter</li>
                  <li className={uPassword.match(/([a-z])/) ? "text-success" : ""}>Lowercase Letter</li>
                  <li className={uPassword.match(/([\d])/) ? "text-success" : ""}>Number</li>
                  <li className={uPassword.length >= 8 ? "text-success" : ""}>Length of 8 characters or more</li>
                  <li className={uPassword === confpassword && uPassword.length > 8 ? "text-success" : ""}>Passwords match</li>
               </ul>
            </small>
            <div className="my-4">
               {!loading ? (
                  <button
                     className={onSuccessfulValidation() ? "btn btn-dark btn-lg btn-block" : "btn btn-secondary btn-lg btn-block"}
                     disabled={onSuccessfulValidation() ? false : true}
                  >
                     {buttonText}
                  </button>
               ) : (
                  <button className="btn btn-dark btn-lg btn-block d-flex align-items-center justify-content-center" disabled>
                     <span className="spinner-border spinner-border-md"></span>
                  </button>
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
