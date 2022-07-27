import React, { useEffect, useState } from "react";
import { AllBucketPermissions, RevokeBucketPermissions } from "../../../ReusableComponents/ReusableFunctions";
import * as AiIcons from "react-icons/ai";

const FolderRevokePermission = ({ objectId, setErrorMsg, setSuccessMsg }) => {
   const [data, setData] = useState([]);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      const getData = async () => {
         await AllBucketPermissions(accessToken, objectId, setData, setErrorMsg);
      };
      getData();
      //eslint-disable-next-line
   }, []);

   const refreshedData = async () => {
      await AllBucketPermissions(accessToken, objectId, setData, setErrorMsg);
   };

   const mapData = data?.map((el, index) => {
      return (
         <div key={index}>
            <div className="d-flex justify-content-between mb-2">
               <span>{el.grantedToEmail}</span>
               <span className="text-danger px-3" style={{ cursor: "pointer" }}>
                  <AiIcons.AiFillCloseCircle
                     className="revoke-perm-icon"
                     onClick={async () => {
                        await RevokeBucketPermissions(accessToken, objectId, el.grantedToId, setErrorMsg, setSuccessMsg);
                        refreshedData();
                     }}
                  />
               </span>
            </div>
            {index !== data.length - 1 && <hr />}
         </div>
      );
   });

   return <div className="px-3">{data.length > 0 ? mapData : "Nothing to show"}</div>;
};

export default FolderRevokePermission;
