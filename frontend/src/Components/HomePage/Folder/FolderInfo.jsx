import React from "react";

const FolderInfo = ({ elem }) => {
   const timeFrame = elem.createdOn.replace("T", " @ ");
   const modified = elem.modifiedOn.replace("T", " @ ");
   const deleted = elem.deletedOn;

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
            <div className="col-sm-3 p-2 font-weight-bold">Created on:</div>
            <small className="col-sm-9 p-2 text-mutted">{timeFrame}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Modified on:</div>
            <small className="col-sm-8 p-2 text-mutted">{modified}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Deleted on:</div>
            <small className="col-sm-8 p-2 text-mutted">{deleted !== null ? deleted.replace("T", " @ ") : "Not Deleted"}</small>
         </div>
         <hr className="m-1" />
         <div className="row mb-4">
            <div className="col-sm-3 p-2 font-weight-bold">Parent:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.parentFolderId !== null ? elem.parentFolderId : "No Parent Folder"}</small>
         </div>
      </div>
   );
};

export default FolderInfo;
