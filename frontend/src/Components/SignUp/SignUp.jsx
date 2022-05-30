import React, { useState, useRef } from "react";
import { Link } from "react-router-dom";
import "./SignUp.css";
import AxiosInstance from "../axios/AxiosInstance";
import logoImage from "../Login/assets/logoblackandwhite.svg";

const SignUp = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [adress, setAdress] = useState("");
  const [password, setPassword] = useState("");
  const [confPassword, setConfPassword] = useState("");

  const firstNameRef = useRef();
  const lastNameRef = useRef();
  const emailRef = useRef();
  const phoneRef = useRef();
  const adressRef = useRef();
  const paswordRef = useRef();
  const confPasswordRef = useRef();

  const handleFirstNameChange = (e) => {
    setFirstName(e.target.value);
  };
  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
  };
  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };
  const handlePhoneChange = (e) => {
    setPhone(e.target.value);
  };
  const handleAdressChange = (e) => {
    setAdress(e.target.value);
  };
  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };
  const handleConfPasswordChange = (e) => {
    setConfPassword(e.target.value);
  };

  return (
    <div className="vh-100 container-fluid d-flex justify-content-center align-items-center  latte">
      <div className="col-sm-10 col-md-7 col-lg-6 col-xl-4 p-5 border login-form-radius shadow bg-white">
        <form>
          <div className="d-flex justify-content-center m-0">
            <img src={logoImage} alt="LOGO" className="little-image-logo" />
          </div>
          <h1 className="text-center">SKY-NEST</h1>
          <p className="mb-3 p-0 text-center text-secondary">Create your account</p>
          <p className="alert alert-danger text-danger text-center d-none"></p>
          <div class="row">
            <div class="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="name"
                  value={firstName}
                  id="firstNameInput"
                  ref={firstNameRef}
                  onChange={handleFirstNameChange}
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="firstNameInput">
                  First Name
                </label>
              </div>
            </div>
            <div class="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="name"
                  value={lastName}
                  ref={lastNameRef}
                  onChange={handleLastNameChange}
                  id="lastNameInput"
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="lastNameInput">
                  Last Name
                </label>
              </div>
            </div>
          </div>
          <div className="form-outline mb-1">
            <input
              type="email"
              value={email}
              ref={emailRef}
              onChange={handleEmailChange}
              id="emailInput"
              className="form-control form-control-lg"
              required
              autoComplete="off"
            />
            <label className="form-label" htmlFor="emailInput">
              Email address
            </label>
          </div>
          <div className="form-outline mb-1">
            <input
              type="number"
              value={phone}
              ref={phoneRef}
              onChange={handlePhoneChange}
              id="phoneInput"
              className="form-control form-control-lg"
              required
              autoComplete="off"
            />
            <label className="form-label" htmlFor="phoneInput">
              Phone number
            </label>
          </div>
          <div className="form-outline mb-1">
            <input
              type="text"
              value={adress}
              ref={adressRef}
              onChange={handleAdressChange}
              id="adressInput"
              className="form-control form-control-lg"
              required
              autoComplete="off"
            />
            <label className="form-label" htmlFor="adressInput">
              Adress
            </label>
          </div>
          <div class="row">
            <div class="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="password"
                  value={password}
                  ref={paswordRef}
                  onChange={handlePasswordChange}
                  id="passwordInput"
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="passwordInput">
                  Password
                </label>
              </div>
            </div>
            <div class="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="password"
                  value={confPassword}
                  ref={confPasswordRef}
                  onChange={handleConfPasswordChange}
                  id="confPasswordInput"
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="confPasswordInput">
                  Confirm Password
                </label>
              </div>
            </div>
          </div>
          {
            <div className="pt-1 mb-1">
              <button className="btn btn-dark btn-lg btn-block">Signup</button>
            </div>
          }
          <div className="mt-5 text-center">
            <p className="m-0">Already have an account? </p>
            <Link to={"/login"} href="#!" className="m-0 btn btn-link">
              Sign in
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SignUp;
