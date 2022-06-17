import React from "react";
import "./Loader.css";

const LoaderAnimation = () => (
   <div className="text-center">
      <div className="spinner-border text-primary loader-circle-size" role="status"></div>
      <br />
      <div className="text-muted loader-text-size">Loading your data...</div>
   </div>
);

export default LoaderAnimation;
