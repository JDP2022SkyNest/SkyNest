import React, { useState } from "react";

const UserCardDetails = ({ info, result, horLine = true, edit, func, type = "text", placeholder = "" }) => {
   const [change, setChange] = useState(false);

   return (
      <>
         <div className="row">
            <div className="col-sm-3 p-2">{info}</div>
            {!edit ? (
               <div className="col-sm-9 p-2 text-mutted">{result}</div>
            ) : (
               <div className="col-sm-9">
                  <input
                     type={type}
                     value={`${result === null ? "" : result}`}
                     onChange={(e) => {
                        if (func) {
                           func(e);
                           setChange(true);
                        }
                     }}
                     placeholder={placeholder}
                     className={`form-control border-white`}
                  />
               </div>
            )}
         </div>
         {horLine && <hr />}
      </>
   );
};

export default UserCardDetails;
