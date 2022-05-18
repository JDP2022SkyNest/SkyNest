import React from "react";
import LoginForm from "./LoginForm";
import LoginSidePage from "./LoginSidePage";

const LoginPage = ({ setFakeToken }) => {
	return (
		<div>
			<div className="container-fluid">
				<div className="row vh-100">
					<div className="col gradient-custom">
						<LoginForm setFakeToken={setFakeToken} />
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
