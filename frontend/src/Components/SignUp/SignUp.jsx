import React, { useState } from "react";
import { Link } from "react-router-dom";
import ROUTES from "../Routes/ROUTES";
import "./SignUp.css";

const SignUp = () => {
	const [firstName, setFirstName] = useState("");
	const [lastName, setLastName] = useState("");
	const [email, setEmail] = useState("");
	const [phone, setPhone] = useState("");
	const [adress, setAdress] = useState("");
	const [password, setPassword] = useState("");
	const [confPassword, setConfPassword] = useState("");

	const RegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

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

	const onFormSubmit = (e) => {
		e.preventDefault();
		if (password.match(RegEx)) {
			console.log("Success");
		} else {
			console.log("Failed");
		}
	};

	return (
		<div className="vh-100 container-fluid d-flex justify-content-center align-items-center color-latte">
			<div className="col-sm-10 col-md-7 col-lg-6 col-xl-4 px-5 py-4 border login-form-radius shadow bg-white">
				<form onSubmit={onFormSubmit}>
					<h2 className="text-center">SKY-NEST</h2>
					<p className="mb-4 p-0 text-center text-secondary">Create your account</p>
					<div className="row">
						<div className="col-md-6">
							<div className="form-outline mb-1">
								<input
									type="name"
									value={firstName}
									id="firstNameInput"
									onChange={handleFirstNameChange}
									className="form-control form-control-lg"
									required
									autoComplete="off"
								/>
								<label className="form-label" htmlFor="firstNameInput">
									First Name <span className="text-danger">*</span>
								</label>
							</div>
						</div>
						<div className="col-md-6">
							<div className="form-outline mb-1">
								<input
									type="name"
									value={lastName}
									onChange={handleLastNameChange}
									id="lastNameInput"
									className="form-control form-control-lg"
									required
									autoComplete="off"
								/>
								<label className="form-label" htmlFor="lastNameInput">
									Last Name <span className="text-danger">*</span>
								</label>
							</div>
						</div>
					</div>
					<div className="form-outline mb-1">
						<input
							type="email"
							value={email}
							onChange={handleEmailChange}
							id="emailInput"
							className="form-control form-control-lg"
							required
							autoComplete="off"
						/>
						<label className="form-label" htmlFor="emailInput">
							Email address <span className="text-danger">*</span>
						</label>
					</div>
					<div className="form-outline mb-1">
						<input
							type="number"
							value={phone}
							onChange={handlePhoneChange}
							id="phoneInput"
							className="form-control form-control-lg"
							required
							autoComplete="off"
						/>
						<label className="form-label" htmlFor="phoneInput">
							Phone number <span className="text-danger">*</span>
						</label>
					</div>
					<div className="form-outline mb-1">
						<input
							type="text"
							value={adress}
							onChange={handleAdressChange}
							id="adressInput"
							className="form-control form-control-lg"
							required
							autoComplete="off"
						/>
						<label className="form-label" htmlFor="adressInput">
							Adress <span className="text-danger">*</span>
						</label>
					</div>
					<div className="row">
						<div className="col-md-6">
							<div className="form-outline mb-1">
								<input
									type="password"
									value={password}
									onChange={handlePasswordChange}
									id="passwordInput"
									className="form-control form-control-lg"
									required
									autoComplete="off"
								/>
								<label className="form-label" htmlFor="passwordInput">
									Password <span className="text-danger">*</span>
								</label>
							</div>
						</div>
						<div className="col-md-6">
							<div className="form-outline mb-1">
								<input
									type="password"
									value={confPassword}
									onChange={handleConfPasswordChange}
									id="confPasswordInput"
									className="form-control form-control-lg"
									required
									autoComplete="off"
								/>
								<label className="form-label" htmlFor="confPasswordInput">
									Confirm Password <span className="text-danger">*</span>
								</label>
							</div>
						</div>
					</div>
					<div className="pt-1 mb-1">
						<button className="btn btn-dark btn-lg btn-block">Sign Up</button>
					</div>
					<div className="mt-4 text-center">
						<p className="m-0">Already have an account? </p>
						<Link to={ROUTES.LOGIN} href="#!" className="m-0 btn btn-link">
							Login
						</Link>
					</div>
				</form>
			</div>
		</div>
	);
};

export default SignUp;
