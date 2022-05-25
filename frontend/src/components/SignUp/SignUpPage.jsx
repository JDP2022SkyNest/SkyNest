import React from "react";
import SignUpForm from "./SignUpForm";
import SignUpSidePage from "./SignUpSidePage";
import "./SignUpPage.css";

const LoginPage = () => {
	return (
		<div>
			<div className="container-fluid">
				<div className="row vh-100">
					<div className="col-6 d-none d-xl-block">
						{/* if we want gradient add 'gradient-custom-side' to div above */}
						<SignUpSidePage />
					</div>
					<div className="col gradient-custom-reverse">
						<SignUpForm />
					</div>
				</div>
			</div>
		</div>
	);
};

export default LoginPage;
