import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";

const NoTokenRoute = ({ accessToken, children }) => {
   if (accessToken) {
      return <Navigate to={ROUTES.HOME} />;
   }
   return children;
};

export default NoTokenRoute;
