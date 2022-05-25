import React, { useEffect, useState } from "react";
import Data from "./Data";
import "../Login/LoginPage.css";
import Loader from "../LoadingScreen/Loader";

const dataForTesting = [
	{ id: 1, title: "File 1", info: "Pictures", size: 124 },
	{ id: 2, title: "File 2", info: "Backups", size: 215 },
	{ id: 3, title: "File 3", info: "Movies", size: 186 },
	{ id: 4, title: "File 4", info: "Youtube", size: 564 },
	{ id: 5, title: "File 5", info: "Some Stuff", size: 765 },
	{ id: 6, title: "File 6", info: "My Folder", size: 245 },
	{ id: 7, title: "File 7", info: "Games", size: 654 },
	{ id: 8, title: "File 8", info: "Tickets", size: 812 },
	{ id: 9, title: "File 9", info: "Radnom Folder", size: 999 },
	{ id: 10, title: "File 10", info: "Scripts", size: 145 },
	{ id: 11, title: "File 11", info: "Udemy Material", size: 667 },
	{ id: 12, title: "File 12", info: "Youtube", size: 744 },
	{ id: 13, title: "File 13", info: "WTF?", size: 881 },
	{ id: 14, title: "File 14", info: "Reddit Files", size: 401 },
	{ id: 15, title: "File 15", info: "Panel", size: 444 },
	{ id: 16, title: "File 16", info: "Facebook pics", size: 201 },
	{ id: 17, title: "File 17", info: "Vacation", size: 399 },
	{ id: 18, title: "File 18", info: "Serbia <3", size: 123 },
	{ id: 19, title: "File 19", info: "Pizza Pics", size: 598 },
	{ id: 20, title: "File 20", info: "Recipes", size: 348 },
	{ id: 21, title: "File 21", info: "Spain!!!", size: 912 },
	{ id: 22, title: "File 22", info: "My Books", size: 5 },
];

const HomePage = ({ setAccessToken }) => {
	const [userData, setUserData] = useState("");
	// On LOG-OUT we delete accessToken from LS and we update our state fo empty string
	// So the App will be re-rendered and LOG-IN screen will be shown
	const onLogOut = () => {
		localStorage.removeItem("accessToken");
		setAccessToken("");
	};

	// When we have real data from a server, this function
	// will use axios.get and fetch data
	const fetchUserData = () => {
		setTimeout(() => {
			setUserData(dataForTesting);
		}, 1500);
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

	useEffect(() => {
		fetchUserData();
	}, []);

	if (userData) {
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
	}
	return <Loader />;
};

export default HomePage;
