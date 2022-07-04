import React, { useEffect } from "react";
import * as TiCions from "react-icons/ti";
import "./Messages.css";
import { alertTimeout } from "./ReusableFunctions";

const SetSuccessMsg = ({
   successMsg,
   setSuccessMsg,
   customStyle = "alert alert-success text-success text-center col-12 col-sm-6 offset-0 offset-sm-3 mt-3 mb-0",
}) => {
   useEffect(() => {
      alertTimeout(3000, setSuccessMsg);
   }, [setSuccessMsg]);

   return (
      <p className={successMsg ? customStyle : "d-none"}>
         {successMsg}
         <TiCions.TiDeleteOutline
            onClick={() => {
               setSuccessMsg("");
            }}
            className="remove-error-icon"
         />
      </p>
   );
};

export default SetSuccessMsg;
