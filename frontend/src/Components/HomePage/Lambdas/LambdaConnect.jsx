import React from "react";
import { lambdaAuth } from "../../ReusableComponents/ReusableFunctions";
import * as FaIcons from "react-icons/fa";

const LambdaConnect = ({ setErrorMsg, setActive }) => {
   const accessToken = localStorage.accessToken;

   return (
      <div
         onClick={() => {
            lambdaAuth(accessToken, setErrorMsg);
            setActive(1);
         }}
         className="ml-1 mr-1 mt-2 btn btn-secondary"
      >
         Connect to Dropbox <FaIcons.FaDropbox className="main-icon-align ml-2" fill="white" />
      </div>
   );
};

export default LambdaConnect;
