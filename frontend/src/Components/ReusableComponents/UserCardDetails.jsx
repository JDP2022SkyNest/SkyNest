import React from "react";

const UserCardDetails = ({ info, result, horLine = true }) => {
   return (
      <>
         <div className="row">
            <div className="col-sm-3">{info}</div>
            <div className="col-sm-9 text-mutted">{result}</div>
         </div>
         {horLine && <hr />}
      </>
   );
};

export default UserCardDetails;
