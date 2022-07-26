import React from "react";
import LoaderAnimation from "./LoaderAnimation";

const Loader = () => (
   <div className="container-fluid d-flex flex-column justify-content-center align-items-center vh-100" style={{ position: "absolute" }}>
      <LoaderAnimation />
   </div>
);

export default Loader;
