import React, { useState } from "react";

const FakeData = [
	{ id: 1, title: "File 1", info: "Pictures", size: 124 },
	{ id: 2, title: "File 2", info: "Backups", size: 215 },
	{ id: 3, title: "File 3", info: "Movies", size: 186 },
	{ id: 4, title: "File 4", info: "Placeholder1", size: 564 },
	{ id: 5, title: "File 5", info: "Some Stuff", size: 765 },
	{ id: 6, title: "File 6", info: "My Folder", size: 245 },
	{ id: 7, title: "File 6", info: "My Folder", size: 654 },
	{ id: 8, title: "File 6", info: "My Folder", size: 812 },
	{ id: 9, title: "File 6", info: "My Folder", size: 999 },
	{ id: 10, title: "File 6", info: "My Folder", size: 145 },
	{ id: 11, title: "File 6", info: "My Folder", size: 667 },
	{ id: 12, title: "File 6", info: "My Folder", size: 744 },
	{ id: 13, title: "File 6", info: "My Folder", size: 881 },
	{ id: 14, title: "File 6", info: "My Folder", size: 123 },
];

const Loader = ({ setFakeData }) => {
	const getFakeDataFunc = () => {
		let RNG = Math.ceil(Math.random() * 5);
		console.log(`Fake Data will come in: ${RNG} seconds`);

		setTimeout(() => {
			setFakeData(FakeData);
		}, RNG * 1000);
	};

	useState(() => {
		getFakeDataFunc();
	}, []);

	return (
		<React.Fragment>
			<div className="container-fluid d-flex flex-column justify-content-center align-items-center vh-100">
				{/* Main Loader Style */}
				<div className="spinner-border text-primary" style={{ width: "5rem", height: "5rem" }} role="status">
					<span className="sr-only">Loading...</span>
				</div>

				{/* This is the optional Loader that we may use. */}
				{/* <div className="spinner-grow" style={{ width: "5rem", height: "5rem" }} role="status">
					<span className="sr-only">Loading...</span>
				</div> */}
				<br />
				<div className="text-muted" style={{ fontSize: "20px" }}>
					Loading your data...
				</div>
			</div>
		</React.Fragment>
	);
};

export default Loader;
