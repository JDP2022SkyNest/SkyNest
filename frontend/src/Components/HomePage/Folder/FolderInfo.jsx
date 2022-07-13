import React from "react";

const FolderInfo = ({ elem }) => {
   return (
      <div className="container-fluid">
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Name:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.name}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Created on:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.createdOn}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">ID:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.id}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Created by:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.createdById}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">BucketID:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.bucketId}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Parent:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.parentFolderId !== null ? elem.parentFolderId : "No Parent Folder"}</small>
         </div>
         <hr className="m-1" />
         <div className="row mb-4">
            <div className="col-sm-3 p-2 font-weight-bold">Visibility:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.isPublic ? "Public" : "Private"}</small>
         </div>
      </div>
   );
};

export default FolderInfo;
