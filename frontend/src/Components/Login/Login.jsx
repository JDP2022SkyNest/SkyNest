import React, { useEffect, useRef, useState } from "react";
import "./Login.css";
import logoImage from "./assets/logoblackandwhite.svg";
import AxiosInstance from "../axios/AxiosInstance";

const Login = ({ setAccessToken }) => {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [errorMsg, setErrorMsg] = useState("");

	const emailRef = useRef();

	const getUserToken = async () => {
		AxiosInstance.post("/login", {
			email: email,
			password: password,
		})
			.then(({ data }) => {
				setAccessToken(data.accessToken);
				localStorage.setItem("accessToken", data.accessToken);
			})
			.catch(({ response }) => {
				if (response.status === 400) {
					setErrorMsg("Incorrect username or password");
				} else if (response.status === 0) {
					setErrorMsg("Server Timeout");
				}
				emailRef.current.focus();
			});
	};

	useEffect(() => {
		emailRef.current.focus();
	}, []);

	useEffect(() => {
		setErrorMsg("");
	}, [email, password]);

	const onFormSubmit = (e) => {
		e.preventDefault();
		getUserToken();

		setEmail("");
		setPassword("");
	};

	return (
		<section className="vh-100 container-fluid page-background-color">
			<div className="container py-5 h-100">
				<div className="row d-flex justify-content-center align-items-center h-100">
					<div className="col col-xl-10">
						<div className="card radius">
							<div className="row g-0">
								<div className="image-logo login-form-radius col-md-6 col-lg-5 d-none d-md-flex justify-content-center align-items-start">
									<img src={logoImage} alt="login form" className="img-fluid image m-auto" />
								</div>
								<div className="col-md-6 col-lg-7 d-flex align-items-center">
									<div className="card-body p-4 p-lg-5 text-black">
										<form onSubmit={onFormSubmit}>
											<div className="d-flex align-items-center mb-3 pb-1">
												<span className="d-flex justify-content-center m-auto">
													<img src={logoImage} alt="logo" className="little-image-logo d-md-none d-xs-flex" />{" "}
													<span className="h1 fw-bold m-auto">SkyNest</span>
												</span>
											</div>
											<h5 className="mb-3 pb-3 text-center text-secondary">Sign into your account</h5>
											<p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
											<div className="form-outline mb-4">
												<input
													type="email"
													onChange={(e) => setEmail(e.target.value)}
													value={email}
													ref={emailRef}
													id="emailInput"
													className="form-control form-control-lg"
													required
												/>
												<label className="form-label" htmlFor="emailInput">
													Email address
												</label>
											</div>
											<div className="form-outline mb-4">
												<input
													type="password"
													onChange={(e) => setPassword(e.target.value)}
													value={password}
													id="passwordInput"
													className="form-control form-control-lg"
													required
												/>
												<label className="form-label" htmlFor="passwordInput">
													Password
												</label>
											</div>
											<div className="pt-1 mb-4">
												<button className="btn btn-dark btn-lg btn-block">Login</button>
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
