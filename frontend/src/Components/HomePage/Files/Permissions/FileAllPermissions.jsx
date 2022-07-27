import React, { useEffect, useState } from "react";
import { AllFilePermissions } from "../../../ReusableComponents/ReusableFunctions";

const FileAllPermissions = ({ objectId, setErrorMsg }) => {
   const [data, setData] = useState([]);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      const getData = async () => {
         await AllFilePermissions(accessToken, objectId, setData, setErrorMsg);
      };
      getData();
      //eslint-disable-next-line
   }, []);

   const mapData = data?.map((el, index) => {
      return (
         <div key={index}>
            <div className="d-flex justify-content-between mb-2">
               <span>{el.grantedToEmail}</span>
               <span className="permissions-color border-bottom">{el.accessName}</span>
            </div>
            {index !== data.length - 1 && <hr />}
         </div>
      );
   });

   return <div className="px-3">{data.length > 0 ? mapData : "Nothing to show"}</div>;
};

export default FileAllPermissions;
