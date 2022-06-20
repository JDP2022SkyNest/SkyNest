import React, { useEffect, useRef, useState } from "react";
import ROUTES from "../Routes/ROUTES";
import { Link, useNavigate } from "react-router-dom";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import AxiosInstance from "../axios/AxiosInstance";
import LoadingButton from "../Loader/LoadingButton";

const ForgotPassword = () => {
   const [email, setEmail] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const emailRef = useRef();
   const navigate = useNavigate();

   const onEmailChange = (e) => {
      setEmail(e.target.value);
   };

   const onFormSubmit = async (e) => {
      e.preventDefault();
      setLoading(true);
      await onSuccessfulChange();
   };

   const onSuccessfulChange = async () => {
      try {
         await AxiosInstance.post(`/public/resend-email?email=${email}`);
         setSuccessMsg("Email has been sent");
         redirectTo(navigate, ROUTES.LOGIN, 1500);
      } catch (err) {
         if (err.response.status === 500) {
            setErrorMsg("User already verified");
         } else if (err.response.status === 404) {
            setErrorMsg("User doesn't exist");
         } else {
            setErrorMsg(err.response.data.messages);
            console.log(err.response.status);
         }
         setLoading(false);
      }
   };

   useEffect(() => {
      emailRef.current.focus();
   }, []);

   useEffect(() => {
      setErrorMsg("");
   }, [email]);

   return (
      <CenteredContainer>
         <form onSubmit={onFormSubmit}>
            <h5 className="mb-4 p-0 text-center text-dark">Please enter your e-mail</h5>
            <fieldset disabled={loading}>
               <div className="form-outline mb-4">
                  <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
                  <p className={successMsg ? "alert alert-success text-success text-center" : "d-none"}>{successMsg}</p>

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
                  {loading ? (
                     <LoadingButton />
                  ) : (
                     <div className="pt-1 mb-4">
                        <button className="btn btn-dark btn-lg btn-block">Submit</button>
                     </div>
                  )}
               </div>
            </fieldset>
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
