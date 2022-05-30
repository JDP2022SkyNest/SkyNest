import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";

const RedirectRoute = ({ accessToken }) => {
	if (!accessToken) {
		return <Navigate to={ROUTES.LOGIN} />;
	}
	return <Navigate to={ROUTES.HOME} />;
};

export default RedirectRoute;
