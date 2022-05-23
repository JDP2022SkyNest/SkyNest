import React from "react";

const Data = ({ userData, colorizeTheData }) => {
	const showUserData = userData.map((data) => {
		// colorizeTheData function make an object with values of different colors
		// and fileSize names for each data in the styles then we use values
		// from an individual object to add cetrain color styles with bootstrap

		return (
			// d-inline-block m-3 - To revert Back if needed
			<div className="col-6 col-md-4 col-lg-3" key={data.id}>
				<div className={`card mb-3`} style={{ maxWidth: "15rem" }}>
					{/* <div className="card-header border-success">File number: {data.id}</div> */}
					<div className={`card-body text-white bg-${colorizeTheData(data).color}`}>
						<h5 className="card-title text-center">{data.title}</h5>
						<p className="card-text">{data.info}</p>
					</div>
					<div className={`text-center card-footer bg-transparent`}>
						<span className={`text-${colorizeTheData(data).color}`}>{colorizeTheData(data).fileSize}</span>
						<span className="font-weight-bold">{data.size} MB</span>
					</div>
				</div>
			</div>
		);
	});

	return <div className="row">{showUserData}</div>;
};

export default Data;
