import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";

const ProtectedRoute = ({ accessToken, children }) => {
   if (!accessToken) {
      return <Navigate to={ROUTES.LOGIN} />;
   }
   return children;
};

export default ProtectedRoute;
