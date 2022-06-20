import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";

const AdminRoute = ({ userRole, children }) => {
   if (userRole === "admin") {
      return children;
   }
   return <Navigate to={ROUTES.HOME} />;
};

export default AdminRoute;
