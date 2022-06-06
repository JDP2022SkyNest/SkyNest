import React, { useEffect, useRef, useState } from "react";
import ROUTES from "../Routes/ROUTES";
import { Link } from "react-router-dom";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import AxiosInstance from "../axios/AxiosInstance";

const ForgotPassword = () => {
   const [email, setEmail] = useState("");
   const emailRef = useRef();

   const onEmailChange = (e) => {
      setEmail(e.target.value);
   };

   const onFormSubmit = (e) => {
      e.preventDefault();
      onSuccessfulChange();
   };

   const onSuccessfulChange = async () => {
      try {
         await AxiosInstance.post("/users/password-reset/request", { email });
         console.log("SUCCESS");
      } catch (err) {
         console.log(err);
      }
   };

   useEffect(() => {
      emailRef.current.focus();
   });

   return (
      <CenteredContainer>
         <form onSubmit={onFormSubmit}>
            <h5 className="mb-4 p-0 text-center text-dark">Please enter your e-mail</h5>
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
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
               />
            </div>
            <div className="pt-1 mb-4">
               <button className="btn btn-dark btn-lg btn-block">Submit</button>
            </div>
            <div className="mt-4 text-center">
               <p className="m-0">Wan't to go back? </p>
               <Link to={ROUTES.LOGIN} className="m-0 btn btn-link">
                  Login Page
               </Link>
            </div>
         </form>
      </CenteredContainer>
   );
};

export default ForgotPassword;
