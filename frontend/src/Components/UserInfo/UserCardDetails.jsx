import React, { useState } from "react";

const UserCardDetails = ({ info, result, horLine = true, edit, func, type = "text" }) => {
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
                     value={result}
                     onChange={(e) => {
                        func(e);
                        setChange(true);
                     }}
                     className={`form-control border-${change ? "warning" : "info"}`}
                  />
               </div>
            )}
         </div>
         {horLine && <hr />}
      </>
   );
};

export default UserCardDetails;
