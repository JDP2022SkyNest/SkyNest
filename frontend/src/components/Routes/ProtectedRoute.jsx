import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ accessToken, children }) => {
	if (!accessToken) {
		return <Navigate to={"/login"} />;
	}
	return children;
};

export default ProtectedRoute;
