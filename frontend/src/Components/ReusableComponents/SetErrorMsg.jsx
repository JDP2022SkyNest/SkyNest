import React, { useEffect } from "react";
import * as TiCions from "react-icons/ti";
import "./Messages.css";
import { alertTimeout } from "./ReusableFunctions";

const SetErrorMsg = ({ errorMsg, setErrorMsg }) => {
   useEffect(() => {
      alertTimeout(3000, setErrorMsg);
   }, [setErrorMsg]);

   return (
      <p className={errorMsg ? "alert alert-danger text-danger text-center col-12 col-sm-6 offset-0 offset-sm-3 mt-3 mb-0" : "d-none"}>
         {errorMsg}
         <TiCions.TiDeleteOutline
            onClick={() => {
               setErrorMsg("");
            }}
            className="remove-error-icon"
         />
      </p>
   );
};

export default SetErrorMsg;
