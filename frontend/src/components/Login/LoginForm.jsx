import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./LoginPage.css";
import AxiosInstance from "../axios/AxiosInstance";

const LoginPage = ({ setAccessToken }) => {
	const [usernameInput, setUsernameInput] = useState("");
	const [passwordInput, setPasswordInput] = useState("");
	const [errorMsg, setErrorMsg] = useState("");
	const [forgotPassword, setForgotPassword] = useState("");

	// Used to navigate to other URLs
	const navigate = useNavigate();

	// Username input , used Ref to Focus it.
	const inputRef = useRef();

	// Fetch User Token
	const getUserToken = async () => {
		AxiosInstance.post("/login", {
			email: usernameInput,
			password: passwordInput,
		})
			.then(({ data }) => {
				setAccessToken(data.accessToken);
				localStorage.setItem("accessToken", data.accessToken);
				navigate("/homepage");
			})
			.catch(({ response }) => {
				if (response.status === 400) {
					setErrorMsg("Incorrect username or password");
				} else if (response.status === 0) {
					setErrorMsg("Server Timeout");
				}
				setForgotPassword("Forgot Password?");
			});
	};

	// Focuset username input on page load
	useEffect(() => {
		inputRef.current.focus();
	}, []);

	// When username and pw inputs are changed, hide error message
	useEffect(() => {
		setErrorMsg("");
	}, [usernameInput, passwordInput]);

	// Func that executes on Form Submit
	const onFormSubmit = (e) => {
		e.preventDefault();
		// Dummy conditional statement, will change this to meet the
		// username and password client requirements (char length, char symbols, numbers etc..)
		if (usernameInput.length > 5 && passwordInput.length > 5) {
			getUserToken();
		} else {
			setErrorMsg("User and pw needs to have more than 5 characters");
		}
	};

	return (
		<div className="container-fluid d-flex justify-content-center align-items-center h-100">
			<div className="col-12 col-sm-8 col-md-7 col-lg-5 col-xl-7 bg-dark p-5" style={{ borderRadius: "20px" }}>
				<div className="text-center pb-5">
					<h1 className="text-light font-weight-bold">LOGIN</h1>
					<small className="text-secondary">Please enter your username and password</small>
				</div>
				<form onSubmit={onFormSubmit}>
					<p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
					<div className="input-group mb-3">
						<div className="input-group-prepend">
							<span className="input-group-text">
								<i className="fa fa-user"></i>
							</span>
						</div>
						<input
							onChange={(e) => setUsernameInput(e.target.value)}
							value={usernameInput}
							type="text"
							className="form-control"
							placeholder="Enter your username"
							ref={inputRef}
							required
						/>
					</div>
					<div className="input-group mb-3">
						<div className="input-group-prepend">
							<span className="input-group-text">
								<i className="fa-solid fa-lock"></i>
							</span>
						</div>
						<input
							onChange={(e) => setPasswordInput(e.target.value)}
							value={passwordInput}
							type="password"
							className="form-control"
							placeholder="Enter your password"
							required
						/>
					</div>
					<p className={forgotPassword ? "d-block btn btn-link btn-sm text-left" : "d-none"}>
						<Link to={"/"}>{forgotPassword}</Link>
					</p>
					<div className="text-center mt-5">
						<button className="btn btn-primary mb-3 mt-1 py-3 px-5 btn-lg d-block w-100">LOGIN</button>
						<p className="mt-4">
							<small className="text-white">Don't have an account?</small>
							<br />
							<Link to={"/signup"} className="btn btn-link">
								Register Here
							</Link>
						</p>
					</div>
				</form>
			</div>
		</div>
	);
};

export default LoginPage;
