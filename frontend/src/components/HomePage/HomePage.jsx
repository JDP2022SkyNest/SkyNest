import React from "react";
import "../Login/LoginPage.css";

const HomePage = ({ setFakeToken, fakeData, setFakeData }) => {
	const onLogOut = () => {
		localStorage.removeItem("accessToken");
		setFakeToken("");
		setFakeData("");
	};

	return (
		<React.Fragment>
			<nav className="navbar sticky-top navbar-expand-md navbar-dark text-light  gradient-custom">
				<div className="container">
					<a href="" className="navbar-brand text-warning">
						SKYNEST
					</a>
					<button className="navbar-toggler" data-toggle="collapse" data-target="#mynav3">
						<span className="navbar-toggler-icon"></span>
					</button>
					<div className="collapse navbar-collapse" id="mynav3">
						<ul className="navbar-nav">
							<li className="nav-item">
								<a href="#" className="nav-link active">
									Home
								</a>
							</li>
							<li className="nav-item">
								<a href="#" className="nav-link">
									About
								</a>
							</li>
						</ul>
						<form onSubmit={(e) => e.preventDefault()} className="form-inline ml-auto">
							<input type="text" className="form-control" />
							<button className="btn btn-warning ml-2">Search</button>
						</form>
						<button className="btn btn-danger ml-2" onClick={onLogOut}>
							Log Out
						</button>
					</div>
				</div>
			</nav>
			<div className="container">
				<div>
					<span className="display-4 text-center d-block my-3 bg-secondary text-success font-weight-bold">TOKEN FOUND</span>
					<div className="text-center display-4">Your Data:</div>
					<div className="text-center h4 text-danger">{fakeData}</div>
				</div>
			</div>
		</React.Fragment>
	);
};

export default HomePage;
