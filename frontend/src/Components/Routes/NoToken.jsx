import React from "react";
import { useContext } from "react";
import { Navigate } from "react-router-dom";
import GlobalContext from "../context/GlobalContext";
import ROUTES from "./ROUTES";

const NoTokenRoute = ({ children }) => {
   const { accessToken } = useContext(GlobalContext);
   if (accessToken) {
      return <Navigate to={ROUTES.HOME} />;
   }
   return children;
};

export default NoTokenRoute;
