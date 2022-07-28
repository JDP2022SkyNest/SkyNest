import React from "react";

const BucketInfo = ({ elem }) => {
   const deleted = elem.deletedOn;
   const fileSize = elem.size / 1024 / 1024;

   return (
      <div className="container-fluid">
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Name:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.name}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Description:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.description}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Created by:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.createdByName}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Deleted on:</div>
            <small className="col-sm-8 p-2 text-mutted">{deleted !== null ? deleted.replace("T", " @ ") : "Not Deleted"}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Size:</div>
            <small className="col-sm-8 p-2 text-mutted">{fileSize < 0.5 ? `${fileSize.toFixed(4)} mb` : `${fileSize.toFixed(2)} mb`}</small>
         </div>
         <hr className="m-1" />
         <div className="row mb-4">
            <div className="col-sm-3 p-2 font-weight-bold">Visibility:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.isPublic ? "Public" : "Private"}</small>
         </div>
      </div>
   );
};

export default BucketInfo;
