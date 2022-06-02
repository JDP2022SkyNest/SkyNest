import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ROUTES from "../Routes/ROUTES";
import password from "secure-random-password";
import AxiosInstance from "../axios/AxiosInstance";
import "./SignUp.css";

const RegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

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

   const navigate = useNavigate();

   const loginPageRedirect = (delay) => {
      setTimeout(() => {
         navigate(ROUTES.LOGIN);
      }, delay);
   };

   const pwSuggestion = (length) => {
      let suggestedPw = password.randomPassword({ length, characters: [password.lower, password.upper, password.digits] });
      setPassword(suggestedPw);
      setConfpassword(suggestedPw);
   };

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
         setSuccessfulRegister("Registration Successful");
         setButtonText("SUCCESSFUL");
         loginPageRedirect(2000);
      } catch ({ response }) {
         if (response.status === 409) {
            setErrorMsg("Email aready exists");
         } else if (response.status === 500) {
            setErrorMsg("Internal Server Error");
         } else if (response.status === 0) {
            setErrorMsg("Server currently offline");
         } else {
            setErrorMsg("Placeholder for other errors");
            console.log(response.status);
         }
      }
   };

   const handleSubmit = (e) => {
      e.preventDefault();
      if (uPassword.match(RegEx) && uPassword === confpassword) {
         userRegistration();
      } else {
         setErrorMsg("Requirements not met");
      }
   };

   const onSuccessfulValidation = () => {
      if (uPassword.match(RegEx) && uPassword === confpassword) {
         return true;
      }
      return false;
   };

   useEffect(() => {
      setErrorMsg("");
   }, [email]);

   return (
      <div className="vh-100 container-fluid d-flex justify-content-center align-items-center color-latte">
         <div className="col-sm-10 col-md-7 col-lg-6 col-xl-4 px-5 py-4 border login-form-radius shadow bg-white">
            <form onSubmit={handleSubmit}>
               <h2 className="text-center">SKY-NEST</h2>
               <p className="mb-4 p-0 text-center text-secondary">Create your account</p>
               <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
               <p className={successfulRegister ? "alert alert-success text-success text-center" : "d-none"}>{successfulRegister}</p>
               <div className="row">
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <label className="form-label" htmlFor="firstNameInput">
                           <small>First Name</small> <span className="text-danger">*</span>
                        </label>
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
                        <label className="form-label" htmlFor="lastNameInput">
                           <small>Last Name</small> <span className="text-danger">*</span>
                        </label>
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
                  <label className="form-label" htmlFor="emailInput">
                     <small>Email address</small> <span className="text-danger">*</span>
                  </label>
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
                  <label className="form-label" htmlFor="phoneInput">
                     <small>Phone number</small> <span className="text-danger">*</span>
                  </label>
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
                  <label className="form-label" htmlFor="adressInput">
                     <small>Home address</small> <span className="text-danger">*</span>
                  </label>
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
                        <label className="form-label position-relative w-100" htmlFor="passwordInput">
                           <small>Password</small> <span className="text-danger">*</span>
                           <small className="suggestPwPosition btn-link" onClick={() => pwSuggestion(10)}>
                              Suggest PW?
                           </small>
                        </label>
                        <input
                           type="password"
                           name="password"
                           value={uPassword}
                           onChange={(e) => setPassword(e.target.value)}
                           id="passwordInput"
                           className="form-control"
                           required
                           autoComplete="off"
                        />
                     </div>
                  </div>
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <label className="form-label" htmlFor="confPasswordInput">
                           <small>Confirm password</small> <span className="text-danger">*</span>
                        </label>
                        <input
                           type="password"
                           name="confPassword"
                           value={confpassword}
                           onChange={(e) => setConfpassword(e.target.value)}
                           id="confPasswordInput"
                           className="form-control"
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
                  <button
                     className={onSuccessfulValidation() ? "btn btn-dark btn-lg btn-block" : "btn btn-secondary btn-lg btn-block"}
                     disabled={onSuccessfulValidation() ? false : true}
                  >
                     {buttonText}
                  </button>
               </div>
               <div className="mt-4 text-center">
                  <p className="m-0">Already have an account? </p>
                  <Link to={ROUTES.LOGIN} href="#!" className="m-0 btn btn-link">
                     Login
                  </Link>
               </div>
            </form>
         </div>
      </div>
   );
};

export default SignUp;
