import React from "react";

const ModalLoader = ({ marginLeft = "" }) => {
   return (
      <button className={`btn btn-dark button-width ${marginLeft && `ml-0 ml-sm-${marginLeft}`}`} disabled>
         <span className="spinner-border spinner-border-sm" />
      </button>
   );
};

export default ModalLoader;
