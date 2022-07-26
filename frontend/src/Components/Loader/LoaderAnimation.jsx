import React from "react";
import "./Loader.css";

const LoaderAnimation = () => (
   <div
      className="text-center loader-color mt-5"
      style={{ position: "absolute", left: "50%", top: "40%", transform: "translate(-50%,-50%)", zIndex: "100" }}
   >
      <div className="spinner-border loader-circle-size" role="status"></div>
      <br />
      {/* <div className="text-muted loader-text-size">Loading your data...</div> */}
   </div>
);

export default LoaderAnimation;
