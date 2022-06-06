import React from "react";

const HomePage = ({ setAccessToken }) => {
   const onLogout = () => {
      localStorage.removeItem("accessToken");
      setAccessToken("");
   };

   return (
      <div className="latte vh-100 d-flex justify-content-center align-items-center">
         <div className="container text-center">
            <h1>PLACEHOLDER HOME PAGE</h1>
            <button className="btn btn-warning" onClick={onLogout}>
               LOGOUT
            </button>
         </div>
      </div>
   );
};

export default HomePage;
