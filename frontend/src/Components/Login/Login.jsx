import React from "react";
import "./Login.css";
import logoImage from "./assets/logoblackandwhite.svg";

export const Login = () => {
  return (
    <section className="vh-100 container-fluid cont">
      <div className="container py-5 h-100">
        <div className="row d-flex justify-content-center align-items-center h-100">
          <div className="col col-xl-10">
            <div className="card radius">
              <div className="row g-0">
                <div className="imageLogo radius col-md-6 col-lg-5 d-none d-md-flex justify-content-center align-items-start">
                  <img src={logoImage} alt="login form" className="img-fluid image" />
                </div>
                <div className="col-md-6 col-lg-7 d-flex align-items-center">
                  <div className="card-body p-4 p-lg-5 text-black">
                    <form>
                      <div className="d-flex align-items-center mb-3 pb-1">
                        <span>
                          <img src={logoImage} alt="logo" className="littleImgLogo d-md-none d-xs-flex" />
                        </span>
                        <span className="h1 fw-bold mb-0">SkyNest</span>
                      </div>
                      <h5 className="fw-normal mb-3 pb-3">Sign into your account</h5>
                      <div className="form-outline mb-4">
                        <input type="email" id="form2Example17" className="form-control form-control-lg" />
                        <label className="form-label" for="form2Example17">
                          Email address
                        </label>
                      </div>
                      <div className="form-outline mb-4">
                        <input type="password" id="form2Example27" className="form-control form-control-lg" />
                        <label className="form-label" for="form2Example27">
                          Password
                        </label>
                      </div>
                      <div className="pt-1 mb-4">
                        <button className="btn btn-dark btn-lg btn-block" type="button">
                          Login
                        </button>
                      </div>
                      <span className="small text-muted" href="#!">
                        Forgot password?
                      </span>
                      <p className="mb-5 pb-lg-2 text-muted">
                        Don't have an account?{" "}
                        <a href="#!" className="text-muted">
                          Register here
                        </a>
                      </p>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};
