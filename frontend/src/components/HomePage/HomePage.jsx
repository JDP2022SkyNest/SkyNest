import React from "react";
import Data from "./Data";
import "../Login/LoginPage.css";

const HomePage = ({ setFakeToken, fakeData, setFakeData }) => {
	// On LOG-OUT we delete accessToken from LS and we update our state fo empty string
	// So the App will be re-rendered and LOG-IN screen will be shown
	const onLogOut = () => {
		localStorage.removeItem("accessToken");
		setFakeToken("");
		setFakeData("");
	};

	return (
		<React.Fragment>
			<nav className="navbar sticky-top navbar-expand-md navbar-dark text-light  gradient-custom">
				<div className="container">
					<a href="/" className="navbar-brand text-warning">
						SKYNEST
					</a>
					<button className="navbar-toggler" data-toggle="collapse" data-target="#mynav3">
						<span className="navbar-toggler-icon"></span>
					</button>
					<div className="collapse navbar-collapse" id="mynav3">
						<ul className="navbar-nav">
							<li className="nav-item">
								<a href="/" className="nav-link active">
									Home
								</a>
							</li>
							<li className="nav-item">
								<a href="/" className="nav-link">
									About
								</a>
							</li>
						</ul>
						<form onSubmit={(e) => e.preventDefault()} className="form-inline ml-auto mb-2 mb-md-0">
							<input type="text" className="form-control d-inline" />
							<button className="btn btn-warning ml-2">Search</button>
						</form>
						<button className="btn btn-danger ml-0 ml-md-2" onClick={onLogOut}>
							Log Out
						</button>
					</div>
				</div>
			</nav>
			<div className="container">
				<div>
					<div className="shadow p-4 mt-3" style={{ borderRadius: "10px", backgroundColor: "whitesmoke" }}>
						{/* OUR DATA FROM SERVER HERE */}
						<Data fakeData={fakeData} />
					</div>
				</div>
			</div>
		</React.Fragment>
	);
};

export default HomePage;
