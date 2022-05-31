import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ROUTES from "../Routes/ROUTES";
import AxiosInstance from "../axios/AxiosInstance";
import "./SignUp.css";

const RegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

const SignUp = () => {
   const [name, setName] = useState("");
   const [surname, setSurname] = useState("");
   const [email, setEmail] = useState("");
   const [phoneNumber, setPhoneNumber] = useState("");
   const [address, setAddress] = useState("");
   const [password, setPassword] = useState("");
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

   const userRegistration = async () => {
      try {
         await AxiosInstance.post("/users/register", {
            email,
            password,
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
         }
      }
   };

   const handleSubmit = (e) => {
      e.preventDefault();
      if (password.match(RegEx) && password === confpassword) {
         userRegistration();
      } else {
         errorMsg("Requirements not met");
      }
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
                        <input
                           type="name"
                           name="firstName"
                           id="firstNameInput"
                           onChange={(e) => setName(e.target.value)}
                           className="form-control form-control"
                           required
                           autoComplete="off"
                        />
                        <label className="form-label" htmlFor="firstNameInput">
                           <small>First Name</small> <span className="text-danger">*</span>
                        </label>
                     </div>
                  </div>
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <input
                           type="name"
                           name="lastName"
                           onChange={(e) => setSurname(e.target.value)}
                           id="lastNameInput"
                           className="form-control form-control"
                           required
                           autoComplete="off"
                        />
                        <label className="form-label" htmlFor="lastNameInput">
                           Last Name <span className="text-danger">*</span>
                        </label>
                     </div>
                  </div>
               </div>
               <div className="form-outline mb-1">
                  <input
                     type="email"
                     name="email"
                     onChange={(e) => setEmail(e.target.value)}
                     id="emailInput"
                     className="form-control form-control"
                     required
                     autoComplete="off"
                  />
                  <label className="form-label" htmlFor="emailInput">
                     Email address <span className="text-danger">*</span>
                  </label>
               </div>
               <div className="form-outline mb-1">
                  <input
                     type="number"
                     name="phoneNumber"
                     onChange={(e) => setPhoneNumber(e.target.value)}
                     id="phoneInput"
                     className="form-control form-control"
                     required
                     autoComplete="off"
                  />
                  <label className="form-label" htmlFor="phoneInput">
                     Phone number <span className="text-danger">*</span>
                  </label>
               </div>
               <div className="form-outline mb-1">
                  <input
                     type="text"
                     name="adress"
                     onChange={(e) => setAddress(e.target.value)}
                     id="adressInput"
                     className="form-control form-control"
                     required
                     autoComplete="off"
                  />
                  <label className="form-label" htmlFor="adressInput">
                     Adress <span className="text-danger">*</span>
                  </label>
               </div>
               <div className="row">
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <input
                           type="password"
                           name="password"
                           onChange={(e) => setPassword(e.target.value)}
                           id="passwordInput"
                           className="form-control form-control"
                           required
                           autoComplete="off"
                        />
                        <label className="form-label" htmlFor="passwordInput">
                           Password <span className="text-danger">*</span>
                        </label>
                     </div>
                  </div>
                  <div className="col-md-6">
                     <div className="form-outline mb-1">
                        <input
                           type="password"
                           name="confPassword"
                           onChange={(e) => setConfpassword(e.target.value)}
                           id="confPasswordInput"
                           className="form-control form-control"
                           required
                           autoComplete="off"
                        />
                        <label className="form-label" htmlFor="confPasswordInput">
                           Confirm Password <span className="text-danger">*</span>
                        </label>
                     </div>
                  </div>
               </div>
               <small>
                  Password requirements:
                  <ul className="mt-2 bt-2 text-danger unordered-list-padding">
                     <li className={password.match(/([A-Z])/) ? "text-success" : ""}>Uppercase Letter</li>
                     <li className={password.match(/([a-z])/) ? "text-success" : ""}>Lowercase Letter</li>
                     <li className={password.match(/([\d])/) ? "text-success" : ""}>Number</li>
                     <li className={password.length >= 8 ? "text-success" : ""}>Length of 8 characters or more</li>
                     <li className={password === confpassword && password.length > 8 ? "text-success" : ""}>Passwords match</li>
                  </ul>
               </small>
               <div className="my-4">
                  <button
                     className={
                        password.match(RegEx) && password === confpassword
                           ? "btn btn-dark btn-lg btn-block  "
                           : "btn btn-dark btn-lg btn-block disabled"
                     }
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
