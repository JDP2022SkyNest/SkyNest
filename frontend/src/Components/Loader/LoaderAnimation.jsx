import React from "react";
import "./Loader.css";

const LoaderAnimation = () => (
   <div className="text-center loader-color">
      <div className="spinner-border loader-circle-size" role="status"></div>
      <br />
      {/* <div className="text-muted loader-text-size">Loading your data...</div> */}
   </div>
);

export default LoaderAnimation;
