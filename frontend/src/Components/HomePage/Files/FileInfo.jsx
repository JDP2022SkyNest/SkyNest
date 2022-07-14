import React from "react";

const FileInfo = ({ elem }) => {
   return (
      <div className="container-fluid">
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Name:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.name}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">ID:</div>
            <small className="col-sm-9 p-2 text-mutted">{elem.id}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Type:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.type}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Craeted:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.createdOn}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Created by:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.createdById}</small>
         </div>
         <hr className="m-1" />
         <div className="row">
            <div className="col-sm-3 p-2 font-weight-bold">Size:</div>
            <small className="col-sm-8 p-2 text-mutted">{elem.size}</small>
         </div>
      </div>
   );
};

export default FileInfo;
