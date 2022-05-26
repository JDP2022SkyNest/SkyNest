import React from "react";

// Our Fake Data - Will be changed when we get our endpoint and data from there

const Loader = () => {
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
