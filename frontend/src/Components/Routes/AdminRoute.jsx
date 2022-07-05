import React from "react";
import { Navigate } from "react-router-dom";
import ROUTES from "./ROUTES";
import ROLE from "../Roles/Roles";
import { useContext } from "react";
import GlobalContext from "../context/GlobalContext";

const AdminRoute = ({ children }) => {
   const { userRole } = useContext(GlobalContext);
   if (userRole === ROLE.ADMIN) {
      return children;
   }
   return <Navigate to={ROUTES.HOME} />;
};

export default AdminRoute;
