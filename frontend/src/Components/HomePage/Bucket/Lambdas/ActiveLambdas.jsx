import React, { useEffect, useState } from "react";
import { activeLambdas } from "../../../ReusableComponents/ReusableFunctions";

const ActiveLambdas = ({ setErrorMsg, bucketId }) => {
   const [data, setData] = useState([]);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      const getData = async () => {
         await activeLambdas(accessToken, setData, bucketId, setErrorMsg);
      };
      getData();
      //eslint-disable-next-line
   }, []);

   const mapData = data?.map((el, index) => {
      return (
         <div key={index}>
            <div className="d-flex justify-content-between mb-2">
               <span>{el}</span>
            </div>
            {index !== data.length - 1 && <hr />}
         </div>
      );
   });

   return <div className="px-3">{data.length > 0 ? mapData : "Nothing to show"}</div>;
};

export default ActiveLambdas;
