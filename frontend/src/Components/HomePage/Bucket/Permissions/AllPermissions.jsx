import React, { useEffect, useState } from "react";
import { AllBucketPermissions } from "../../../ReusableComponents/ReusableFunctions";

const AllPermissions = ({ objectId, setErrorMsg }) => {
   const [data, setData] = useState([]);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      const getData = async () => {
         await AllBucketPermissions(accessToken, objectId, setData, setErrorMsg);
      };
      getData();
      //eslint-disable-next-line
   }, []);

   const mapData = data?.map((el, index) => {
      return (
         <div key={index} className="d-flex justify-content-between mb-2">
            <span>{el.grantedToEmail}</span>
            <span className="permissions-color font-weight-bold border-bottom">{el.accessName}</span>
         </div>
      );
   });

   return <div className="px-3">{data.length > 0 ? mapData : "Nothing to show"}</div>;
};

export default AllPermissions;
