import React from "react";
import { useContext } from "react";
import { Navigate } from "react-router-dom";
import GlobalContext from "../context/GlobalContext";
import ROUTES from "./ROUTES";

const ProtectedRoute = ({ children }) => {
   const { accessToken } = useContext(GlobalContext);
   if (!accessToken) {
      return <Navigate to={ROUTES.LOGIN} />;
   }
   return children;
};

export default ProtectedRoute;
