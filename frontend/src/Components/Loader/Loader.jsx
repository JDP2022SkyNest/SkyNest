import React from "react";
import "./Loader.css";

const Loader = () => (
	<div className="container-fluid d-flex flex-column justify-content-center align-items-center vh-100">
		<div className="spinner-border text-primary loader-circle-size" role="status">
			<span className="sr-only">Loading...</span>
		</div>
		<br />
		<div className="text-muted loader-text-size">Loading your data...</div>
	</div>
);

export default Loader;
