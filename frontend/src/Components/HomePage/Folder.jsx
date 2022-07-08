import React, { useState } from "react";
import { OverlayTrigger, Tooltip, Dropdown } from "react-bootstrap";
import * as BsCions from "react-icons/bs";
import { deleteBucket } from "../ReusableComponents/ReusableFunctions";

const Folder = ({ elem, index, refreshBuckets, setErrorMsg, setSuccessMsg }) => {
   const [youSure, setYouSure] = useState(false);
   const accessToken = localStorage.accessToken;

   return (
      <div className="col-12 col-sm-6 col-md-4 col-lg-3 p-1">
         <div key={index} className="card custom-rounded">
            <div className="card-body p-2 px-3">
               <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.name}</Tooltip>}>
                  <h5 className="card-title">{elem.name.length > 16 ? `${elem.name.slice(0, 16)}...` : elem.name}</h5>
               </OverlayTrigger>
               <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.description}</Tooltip>}>
                  <small className="text-muted">{elem.description.length > 18 ? `${elem.description.slice(0, 18)}...` : elem.description}</small>
               </OverlayTrigger>
            </div>
            <div>
               <Dropdown>
                  <Dropdown.Toggle>
                     <BsCions.BsThreeDotsVertical className="dots-icon" aria-expanded="false" />
                  </Dropdown.Toggle>
                  <Dropdown.Menu>
                     <Dropdown.Item className="text-dark">Bucket Info</Dropdown.Item>
                     <Dropdown.Item className="text-dark">Edit bucket</Dropdown.Item>
                     <Dropdown.Item
                        onClick={async () => {
                           await deleteBucket(accessToken, elem.bucketId, setErrorMsg, setSuccessMsg);
                           refreshBuckets();
                        }}
                        className="text-dark"
                     >
                        Delete bucket
                     </Dropdown.Item>
                  </Dropdown.Menu>
               </Dropdown>
            </div>
         </div>
      </div>
   );
};

export default Folder;
