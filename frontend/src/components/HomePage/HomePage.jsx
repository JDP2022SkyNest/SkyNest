import React from "react";
import Data from "./Data";
import "../Login/LoginPage.css";

const HomePage = ({ setAccessToken, userData, setUserData }) => {
	// On LOG-OUT we delete accessToken from LS and we update our state fo empty string
	// So the App will be re-rendered and LOG-IN screen will be shown
	const onLogOut = () => {
		localStorage.removeItem("accessToken");
		setAccessToken("");
		setUserData("");
	};

	let colorizeTheData = (data) => {
		// Intentianally made this obj short-named
		let dataObj = {};
		dataObj.color = "success";
		dataObj.fileSize = "Small - ";
		if (data.size >= 200 && data.size < 400) {
			dataObj.color = "primary";
			dataObj.fileSize = "Medium - ";
		} else if (data.size >= 400 && data.size < 600) {
			dataObj.color = "warning";
			dataObj.fileSize = "Large - ";
		} else if (data.size >= 600) {
			dataObj.color = "danger";
			dataObj.fileSize = "Giga - ";
		}
		return dataObj;
	};

	return (
		<React.Fragment>
			<nav className="navbar sticky-top navbar-expand-md navbar-dark text-light bg-dark">
				<div className="container">
					<a href="/" className="navbar-brand text-warning">
						SKYNEST
					</a>
					<button className="navbar-toggler" data-toggle="collapse" data-target="#mynav3">
						<span className="navbar-toggler-icon"></span>
					</button>
					<div className="collapse navbar-collapse justify-content-between" id="mynav3">
						<ul className="navbar-nav ">
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
						<form onSubmit={(e) => e.preventDefault()} className="form-inline mb-2 mb-md-0">
							<input type="text" className="form-control d-inline" />
							<button className="btn btn-outline-warning ml-2">Search</button>
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
						<Data userData={userData} colorizeTheData={colorizeTheData} />
					</div>
				</div>
			</div>
		</React.Fragment>
	);
};

export default HomePage;
