import React from "react";

const LoadingButton = () => {
   return (
      <div className="pt-1">
         <button className="btn btn-dark btn-lg btn-block d-flex align-items-center justify-content-center" disabled>
            <span className="spinner-border spinner-border-md"></span>
         </button>
      </div>
   );
};

export default LoadingButton;
