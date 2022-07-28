import React from "react";

const FileInfo = ({ elem }) => {
   const timeFrame = elem.createdOn.replace("T", " @ ");
   const fileSize = elem.size / 1024 / 1024;

   return (
      <div className="container-fluid">
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Name:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.name}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Created by:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.createdByEmail}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Type:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.type}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Created:</div>
            <small className="col-sm-8 p-2 text-mutted">{timeFrame}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Size:</div>
            <small className="col-sm-8 p-2 text-mutted">{fileSize < 0.5 ? `${fileSize.toFixed(4)} mb` : `${fileSize.toFixed(2)} mb`}</small>
         </div>
      </div>
   );
};

export default FileInfo;
