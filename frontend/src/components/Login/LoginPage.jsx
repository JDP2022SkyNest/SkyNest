import React from "react";
import LoginForm from "./LoginForm";
import LoginSidePage from "./LoginSidePage";
import "./LoginPage.css";

const LoginPage = ({ setAccessToken }) => {
	return (
		<div>
			<div className="container-fluid">
				<div className="row vh-100">
					<div className="col gradient-custom">
						<LoginForm setAccessToken={setAccessToken} />
					</div>
					<div className="col-6 d-none d-xl-block gradient-custom-side">
						<LoginSidePage />
					</div>
				</div>
			</div>
		</div>
	);
};

export default LoginPage;
