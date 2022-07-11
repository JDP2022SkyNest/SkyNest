import React from "react";

const BucketInfo = ({ elem }) => {
   return (
      <div className="container-fluid">
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Name:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.name}</small>
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Description:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.description}</small>
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">BucketID:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.bucketId}</small>
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">CompanyID:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.companyId}</small>
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Created by:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.createdById}</small>
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Size:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.size}</small>
         </div>
         <hr />
         <div className="row mb-4">
            <div className="col-sm-3 p-2 font-weight-bold">Visibility:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.isPublic ? "Public" : "Private"}</small>
         </div>
      </div>
   );
};

export default BucketInfo;
