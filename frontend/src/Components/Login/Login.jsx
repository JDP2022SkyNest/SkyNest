import React from "react";
import "./Login.css";
import logoImage from "./assets/logoblackandwhite.svg";

const Login = () => {
	return (
		<section className="vh-100 container-fluid pageBackgroudColor">
			<div className="container py-5 h-100">
				<div className="row d-flex justify-content-center align-items-center h-100">
					<div className="col col-xl-10">
						<div className="card radius">
							<div className="row g-0">
								<div className="imageLogo loginFormRadius col-md-6 col-lg-5 d-none d-md-flex justify-content-center align-items-start">
									<img src={logoImage} alt="login form" className="img-fluid image m-auto" />
								</div>
								<div className="col-md-6 col-lg-7 d-flex align-items-center">
									<div className="card-body p-4 p-lg-5 text-black">
										<form>
											<div className="d-flex align-items-center mb-3 pb-1">
												<span className="d-flex justify-content-center m-auto">
													<img src={logoImage} alt="logo" className="littleImgLogo d-md-none d-xs-flex" />
													<span className="h1 fw-bold m-auto">SkyNest</span>
												</span>
											</div>
											<h5 className="mb-3 pb-3 text-center text-secondary">Sign into your account</h5>
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
											<div className="mt-2 text-center">
												<p className="">Don't have an account? </p>
												<p href="#!" className="btn btn-link">
													Register here
												</p>
											</div>
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

export default Login;
