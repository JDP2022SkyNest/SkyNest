import React from "react";
import { Navigate } from "react-router-dom";

const RedirectRoute = ({ accessToken }) => {
	if (!accessToken) {
		return <Navigate to={"/login"} />;
	}
	return <Navigate to={"/homepage"} />;
};

export default RedirectRoute;
