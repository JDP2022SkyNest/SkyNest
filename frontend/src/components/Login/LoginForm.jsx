import React, { useEffect, useRef, useState } from "react";
import "./LoginPage.css";

const LoginPage = ({ setFakeToken }) => {
	const [textInput, setTextInput] = useState("");
	const [passwordInput, setPasswordInput] = useState("");
	const [errorMsg, setErrorMsg] = useState("");
	const [usernameMsg, setusernameMsg] = useState("");

	const inputRef = useRef();

	const fakeTokenFunc = () => {
		setFakeToken("FAKE_TOKEN_VALUE");
		localStorage.setItem("accessToken", "FAKE_TOKEN_VALUE");
	};

	useEffect(() => {
		inputRef.current.focus();
	}, []);

	useEffect(() => {
		setErrorMsg("");
		setusernameMsg("");
	}, [textInput, passwordInput]);

	const onFormSubmit = (e) => {
		e.preventDefault();
		if (textInput.length > 5 && passwordInput.length > 5) {
			fakeTokenFunc();
		} else {
			setErrorMsg("User and PW > 5 chars");
		}
	};

	return (
		<div className="container-fluid d-flex justify-content-center align-items-center h-100">
			<div className="col-12 col-sm-8 col-md-7 col-lg-5 col-xl-7 bg-dark p-5" style={{ borderRadius: "20px" }}>
				<div className="text-center pb-5">
					<h1 className="text-light font-weight-bold">LOGIN</h1>
					<small className="text-secondary">Please enter your e-mail and password</small>
				</div>
				<form onSubmit={onFormSubmit}>
					<p className={errorMsg ? "d-block text-danger" : "d-none"}>{errorMsg}</p>
					<div className="input-group mb-3">
						<div className="input-group-prepend">
							<span className="input-group-text">
								<i className="fa-solid fa-at"></i>
							</span>
						</div>
						<input
							onChange={(e) => setTextInput(e.target.value)}
							value={textInput}
							type="text"
							className="form-control"
							placeholder="Enter your email"
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
					<p className={usernameMsg ? "d-block text-danger" : "d-none"}>{usernameMsg}</p>
					<div className="text-center mt-5">
						<button className="btn btn-primary mb-3 mt-1 py-3 px-5 btn-lg">LOGIN</button>
						<p className="mt-4">
							<small className="text-white">Don't have an account?</small>
							<br />
							<a href="#">Register Here</a>
						</p>
					</div>
				</form>
			</div>
		</div>
	);
};

export default LoginPage;
