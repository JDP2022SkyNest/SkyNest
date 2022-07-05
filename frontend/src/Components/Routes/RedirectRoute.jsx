import React from "react";
import { useContext } from "react";
import { Navigate } from "react-router-dom";
import GlobalContext from "../context/GlobalContext";
import ROUTES from "./ROUTES";

const RedirectRoute = () => {
   const { accessToken } = useContext(GlobalContext);
   if (!accessToken) {
      return <Navigate to={ROUTES.LOGIN} />;
   }
   return <Navigate to={ROUTES.HOME} />;
};

export default RedirectRoute;
