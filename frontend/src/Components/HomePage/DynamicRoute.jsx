import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { bucketContent } from "../ReusableComponents/ReusableFunctions";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import Footer from "../Footer/Footer";

const DynamicRoute = () => {
   const { routeId } = useParams();
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      bucketContent(accessToken, routeId);
   }, [routeId, accessToken]);

   return (
      <div className="dynamic-route-body">
         <NavbarPanel name="User Info" searchBar={false} path={ROUTES.HOME} />
         <Footer />
      </div>
   );
};

export default DynamicRoute;
