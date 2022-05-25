import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import "./SignUpPage";

const LoginPage = () => {
	const [nameInput, setNameInputt] = useState("");
	const [lastName, setLastName] = useState("");
	const [usernameInput, setUsernameInput] = useState("");
	const [passwordInput, setPasswordInput] = useState("");
	const [errorMsg, setErrorMsg] = useState("");
	const [usernameMsg, setusernameMsg] = useState("");

	const inputRef = useRef();

	const logUserData = () => {
		console.table([{ nameInput }, { lastName }, { usernameInput }, { passwordInput }]);
	};

	useEffect(() => {
		inputRef.current.focus();
	}, []);

	useEffect(() => {
		setErrorMsg("");
		setusernameMsg("");
	}, [usernameInput, passwordInput]);

	const onFormSubmit = (e) => {
		e.preventDefault();
		if (usernameInput.length > 5 && passwordInput.length > 5) {
			logUserData();
		} else {
			setErrorMsg("TEST ERROR WHILE SIGN-UP");
		}
	};

	return (
		<div className="container-fluid d-flex justify-content-center align-items-center h-100">
			<div className="col-12 col-sm-8 col-md-7 col-lg-5 col-xl-7 bg-dark p-5" style={{ borderRadius: "20px" }}>
				<div className="text-center pb-5">
					<h1 className="text-light font-weight-bold">SIGN IN</h1>
					<small className="text-secondary">Please enter your information</small>
				</div>
				<form onSubmit={onFormSubmit}>
					<p className={errorMsg ? "d-block text-danger" : "d-none"}>{errorMsg}</p>
					<div className="row">
						<div className="col-6">
							<div className="input-group mb-3">
								<input
									onChange={(e) => setNameInputt(e.target.value)}
									value={nameInput}
									type="text"
									className="form-control"
									placeholder="Name"
									required
								/>
							</div>
						</div>
						<div className="col-6">
							<div className="input-group mb-3">
								<input
									onChange={(e) => setLastName(e.target.value)}
									value={lastName}
									type="text"
									className="form-control"
									placeholder="Last Name"
									required
								/>
							</div>
						</div>
					</div>
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
					<p className={usernameMsg ? "d-block text-danger" : "d-none"}>{usernameMsg}</p>
					<div className="text-center mt-5">
						<button className="btn btn-primary mb-3 mt-1 py-3 px-5 btn-lg">SIGN UP</button>
						<p className="mt-4">
							<small className="text-white">Already have an account?</small>
							<br />
							<Link to={"/login"} href="#">
								Go back to Login screen
							</Link>
						</p>
					</div>
				</form>
			</div>
		</div>
	);
};

export default LoginPage;
