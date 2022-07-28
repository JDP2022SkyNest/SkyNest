import React, { useState } from "react";
import { lambdaFinish } from "../../ReusableComponents/ReusableFunctions";
import Label from "../../ReusableComponents/Label";
import ModalLoader from "../../Loader/ModalLoader";

const LambdaAuth = ({ setErrorMsg, setSuccessMsg, handleClose }) => {
   const [lambdaCode, setLambdaCode] = useState("");
   const [loading, setLoading] = useState(false);
   const accessToken = localStorage.accessToken;

   const onSubmit = async () => {
      if (lambdaCode.length > 5) {
         setLoading(true);
         await lambdaFinish(accessToken, lambdaCode, setErrorMsg, setSuccessMsg, handleClose);
         setLoading(false);
      } else {
         setErrorMsg("Invalid Authorization code length");
      }
   };

   return (
      <div className="container">
         <div className="form-outline mb-1 d-inline-block">
            <Label id="authInp">Authorization:</Label>
            <input
               type="name"
               name="lastName"
               value={lambdaCode}
               onChange={(e) => {
                  setLambdaCode(e.target.value);
               }}
               id="authInp"
               className="form-control mr-1"
               style={{ position: "relative", top: "3px" }}
               required
               autoComplete="off"
            />
         </div>
         {!loading ? (
            <div onClick={onSubmit} className="ml-0 ml-sm-3 btn btn-secondary">
               Authorize
            </div>
         ) : (
            <ModalLoader marginLeft="3" />
         )}
      </div>
   );
};

export default LambdaAuth;
