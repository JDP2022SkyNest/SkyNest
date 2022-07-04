import React, { useEffect } from "react";
import * as TiCions from "react-icons/ti";
import "./Messages.css";
import { alertTimeout } from "./ReusableFunctions";

const SetWarningMsg = ({ warningMsg, setWarningMsg }) => {
   useEffect(() => {
      if (warningMsg !== "") {
         alertTimeout(3000, setWarningMsg);
      }
      //eslint-disable-next-line
   }, [warningMsg]);

   return (
      <p className={warningMsg ? "alert alert-warning text-dark text-center col-12 col-sm-6 offset-0 offset-sm-3 mt-3 mb-0" : "d-none"}>
         {warningMsg}
         <TiCions.TiDeleteOutline
            onClick={() => {
               setWarningMsg("");
            }}
            className="remove-error-icon"
         />
      </p>
   );
};

export default SetWarningMsg;
