import React, { useEffect } from "react";
import * as TiCions from "react-icons/ti";
import "./Messages.css";
import { alertTimeout } from "./ReusableFunctions";

const SetInfoMsg = ({
   infoMsg,
   setInfoMsg,
   customStyle = "alert alert-danger text-danger text-center col-sm-6 offset-0 offset-sm-3 mt-3 mb-0",
}) => {
   useEffect(() => {
      if (infoMsg !== "") {
         alertTimeout(3000, setInfoMsg);
      }
      //eslint-disable-next-line
   }, [infoMsg]);

   return (
      <p className={infoMsg ? `${customStyle} col-12` : "d-none"}>
         {infoMsg}
         <TiCions.TiDeleteOutline
            onClick={() => {
               setInfoMsg("");
            }}
            className="remove-error-icon"
         />
      </p>
   );
};

export default SetInfoMsg;
