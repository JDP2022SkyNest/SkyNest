import React, { useEffect } from "react";

// Our Fake Data - Will be changed when we get our endpoint and data from there
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

const Loader = ({ setUserData }) => {
	// Fake Data that will come in X seconds - Testing purposes
	// This will be deleted as soon as we get our endpoint and real data
	const getTestingData = () => {
		let RNG = Math.ceil(Math.random() * 1);
		console.log(`Fake Data will come in: ${RNG} seconds`);

		setTimeout(() => {
			setUserData(dataForTesting);
		}, RNG * 1000);
	};

	// Load it at first render of this Component
	useEffect(() => {
		getTestingData();
	}, []);

	return (
		// Loader animation with Text - Can be changed whenever, however
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
	);
};

export default Loader;
