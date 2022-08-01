import React, { useEffect, useState } from "react";
import { getAllLambdas, activateTheLambda, deactivateTheLambda } from "../../../ReusableComponents/ReusableFunctions";

const AllLambdas = ({ setErrorMsg, setSuccessMsg, bucketId }) => {
   const [data, setData] = useState([]);
   const accessToken = localStorage.accessToken;

   const overflowStlye = {
      whiteSpace: "nowrap",
      overflow: "hidden",
      textOverflow: "ellipsis",
   };

   useEffect(() => {
      const getData = async () => {
         await getAllLambdas(accessToken, setData, setErrorMsg);
      };
      getData();
      //eslint-disable-next-line
   }, []);

   const mapData = data?.map((el, index) => {
      return (
         <div key={index}>
            <div className="mb-2" style={overflowStlye}>
               <span>{el.name}</span>
               <div className="mt-2">
                  <div
                     onClick={async () => {
                        await activateTheLambda(accessToken, bucketId, el.code, setErrorMsg, setSuccessMsg);
                     }}
                     className="btn btn-sm btn-secondary"
                  >
                     Activate
                  </div>
                  <div
                     onClick={async () => {
                        await deactivateTheLambda(accessToken, bucketId, el.code, setErrorMsg, setSuccessMsg);
                     }}
                     className="ml-2 btn btn-sm btn-outline-secondary"
                  >
                     Deactivate
                  </div>
               </div>
            </div>
            {index !== data.length - 1 && <hr />}
         </div>
      );
   });

   return <div className="px-3">{data.length > 0 ? mapData : "Nothing to show"}</div>;
};

export default AllLambdas;
