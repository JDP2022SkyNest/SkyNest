import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";
import ROLE from "../Roles/Roles";

const AdminRoute = ({ userRole, children }) => {
   if (userRole === ROLE.ADMIN) {
      return children;
   }
   return <Navigate to={ROUTES.HOME} />;
};

export default AdminRoute;
