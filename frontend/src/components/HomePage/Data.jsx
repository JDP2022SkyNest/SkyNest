import React from "react";

const Data = ({ fakeData }) => {
	
	// Different colors depending of the File size
	const allFakeData = fakeData.map((data) => {
		let color = "success";
		if (data.size >= 200 && data.size < 400) {
			color = "primary";
		} else if (data.size >= 400 && data.size < 600) {
			color = "warning";
		} else if (data.size >= 600) {
			color = "danger";
		}

		return (
			// d-inline-block m-3 - To revert Back if needed
			<div className="col-6 col-md-4 col-lg-3" key={data.id}>
				<div className={`card border-${color} mb-3`} style={{ maxWidth: "15rem" }}>
					{/* <div className="card-header border-success">File number: {data.id}</div> */}
					<div className={`card-body text-white bg-${color}`}>
						<h5 className="card-title text-center">{data.title}</h5>
						<p className="card-text">{data.info}</p>
					</div>
					<div className={`text-center text-${color} card-footer bg-transparent border-${color}`}>{data.size} mb</div>
				</div>
			</div>
		);
	});

	return <div className="row">{allFakeData}</div>;
};

export default Data;
