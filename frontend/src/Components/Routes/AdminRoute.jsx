import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";

const AdminRoute = ({ children }) => {
   const userRole = localStorage.USER_ROLE;
   if (userRole === "role_admin") {
      return children;
   }
   return <Navigate to={ROUTES.HOME} />;
};

export default AdminRoute;
