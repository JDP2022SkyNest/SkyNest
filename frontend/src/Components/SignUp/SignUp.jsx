import React from "react";
import { Link } from "react-router-dom";
import "./SignUp.css";

const SignUp = () => {
  return (
    <div className="latte vh-100 d-flex justify-content-center align-items-center">
      <div className="container text-center">
        <h1>SIGNUP PAGE!</h1>
        <Link to="/login" className="btn btn-link btn-lg">
          To Login Page
        </Link>
      </div>
    </div>
  );
};

export default SignUp;
