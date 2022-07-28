import React, { useState } from "react";
import { lambdaFinish } from "../../ReusableComponents/ReusableFunctions";
import Label from "../../ReusableComponents/Label";

const LambdaAuth = () => {
   const [lambdaCode, setLambdaCode] = useState("");
   const accessToken = localStorage.accessToken;

   return (
      <div className="container">
         <div className="form-outline mb-1 d-inline-block">
            <Label id="authInp">Authorization:</Label>
            <input
               type="name"
               name="lastName"
               value={lambdaCode}
               onChange={(e) => {
                  console.log(lambdaCode);
                  setLambdaCode(e.target.value);
               }}
               id="authInp"
               className="form-control"
               required
               autoComplete="off"
            />
         </div>
         <div
            onClick={() => {
               console.log(accessToken);
               console.log(lambdaCode);
               lambdaFinish(accessToken, lambdaCode);
            }}
            className="ml-1 btn btn-secondary"
            style={{ position: "relative", top: "-3px" }}
         >
            Authorize
         </div>
      </div>
   );
};

export default LambdaAuth;
