import React, { useState } from "react";

const Loader = ({ setFakeData }) => {
	const getFakeDataFunc = () => {
		let RNG = Math.ceil(Math.random() * 5);
		console.log(`Fake Data will come in: ${RNG} seconds`);

		setTimeout(() => {
			setFakeData("FAKE_DATA");
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
