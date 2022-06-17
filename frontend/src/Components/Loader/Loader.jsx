import React from "react";
import LoaderAnimation from "./LoaderAnimation";

const Loader = () => (
   <div className="container-fluid d-flex flex-column justify-content-center align-items-center vh-100">
      <LoaderAnimation />
   </div>
);

export default Loader;
