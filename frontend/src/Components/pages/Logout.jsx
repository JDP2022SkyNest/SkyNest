import React from "react";
import CenteredContainer from "../ReusableComponents/CenteredContainer";

const Logout = ({ setAccessToken }) => {
   const onLogout = () => {
      localStorage.removeItem("accessToken");
      setAccessToken("");
   };
   return (
      <CenteredContainer>
         <button className="btn btn-warning" onClick={onLogout}>
            LOGOUT
         </button>
      </CenteredContainer>
   );
};

export default Logout;
