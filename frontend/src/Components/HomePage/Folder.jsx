import React from "react";
import { OverlayTrigger, Tooltip, Button } from "react-bootstrap";

const Folder = ({ elem }) => {
   return (
      <div className="card col-12" style={{ display: "table" }}>
         <div className="card-body p-2">
            <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.name}</Tooltip>}>
               <h5 className="card-title">{elem.name.length > 15 ? `${elem.name.slice(0, 15)}...` : elem.name}</h5>
            </OverlayTrigger>
            <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.description}</Tooltip>}>
               <small className="text-muted">{elem.description.length > 15 ? `${elem.description.slice(0, 15)}...` : elem.description}</small>
            </OverlayTrigger>
         </div>
      </div>
   );
};

export default Folder;
