import React from "react";
import CenteredContainer from "../ReusableComponents/CenteredContainer";

const HomePage = ({ setAccessToken }) => {
   const onLogout = () => {
      localStorage.removeItem("accessToken");
      setAccessToken("");
   };

   return (
      <CenteredContainer>
         <div className="container text-center">
            <h1>PLACEHOLDER HOME PAGE</h1>
            <button className="btn btn-warning" onClick={onLogout}>
               LOGOUT
            </button>
         </div>
      </CenteredContainer>
   );
};

export default HomePage;
