import React, { useEffect } from "react";
import * as TiCions from "react-icons/ti";
import "./Messages.css";
import { alertTimeout } from "./ReusableFunctions";

const SetErrorMsg = ({
   errorMsg,
   setErrorMsg,
   customStyle = "alert alert-danger text-danger text-center col-sm-6 offset-0 offset-sm-3 mt-3 mb-0",
}) => {
   useEffect(() => {
      if (errorMsg !== "") {
         alertTimeout(3000, setErrorMsg);
      }
      //eslint-disable-next-line
   }, [errorMsg]);

   return (
      <p className={errorMsg ? `${customStyle} col-12` : "d-none"}>
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
