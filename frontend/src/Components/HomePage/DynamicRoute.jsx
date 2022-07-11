import React, { useEffect } from "react";
import { useParams } from "react-router-dom";
import { bucketContent } from "../ReusableComponents/ReusableFunctions";

const DynamicRoute = () => {
   const { routeId } = useParams();
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      bucketContent(accessToken, routeId);
   }, [routeId, accessToken]);

   return <div>DynamicRoute</div>;
};

export default DynamicRoute;
