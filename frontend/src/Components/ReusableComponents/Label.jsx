import React from "react";

const Label = ({ children }) => {
   return (
      <label className="form-label mb-1 mt-2" htmlFor="firstNameInput">
         <small>{children}</small> <span className="text-danger">*</span>
      </label>
   );
};
export default Label;
