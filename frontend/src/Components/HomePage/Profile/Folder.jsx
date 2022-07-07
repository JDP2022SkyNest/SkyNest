import React from "react";

const Folder = ({ elem }) => {
   return (
      <div className="card col-12" style={{ display: "table" }}>
         <div className="card-body p-2">
            <h5 className="card-title">{elem.name.length > 15 ? `${elem.name.slice(0, 15)}...` : elem.name}</h5>
            <small className="text-muted">{elem.description.length > 15 ? `${elem.description.slice(0, 15)}...` : elem.description}</small>
         </div>
      </div>
   );
};

export default Folder;
