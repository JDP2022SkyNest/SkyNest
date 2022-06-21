import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";

const AdminRoute = ({ userRole, children }) => {
   if (userRole === "role_admin") {
      return children;
   }
   return <Navigate to={ROUTES.HOME} />;
};

export default AdminRoute;
