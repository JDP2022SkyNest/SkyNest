import React, { useState } from "react";
import { OverlayTrigger, Tooltip, Dropdown, Modal } from "react-bootstrap";
import * as BsCions from "react-icons/bs";
import { deleteBucket } from "../ReusableComponents/ReusableFunctions";
import BucketInfo from "./BucketInfo";
import EditBucketModal from "./EditBucketModal";

const Folder = ({ elem, index, refreshBuckets, setErrorMsg, setSuccessMsg }) => {
   const [show, setShow] = useState(false);
   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
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
                     <Dropdown.Item
                        onClick={() => {
                           handleShow();
                        }}
                        className="text-dark"
                     >
                        Bucket Info
                     </Dropdown.Item>
                     <Dropdown.Item className="text-dark">
                        <EditBucketModal refreshBuckets={refreshBuckets} elem={elem} />
                     </Dropdown.Item>
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
         <Modal show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <BucketInfo elem={elem} />

               <div className="mt-4 d-flex justify-content-end">
                  <button
                     onClick={(e) => {
                        e.preventDefault();
                        handleClose();
                     }}
                     className="ml-2 btn btn-secondary button-width"
                  >
                     Close
                  </button>
               </div>
            </Modal.Body>
         </Modal>
      </div>
   );
};

export default Folder;
