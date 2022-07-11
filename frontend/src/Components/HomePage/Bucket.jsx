import React, { useState } from "react";
import { OverlayTrigger, Tooltip, Dropdown, Modal } from "react-bootstrap";
import * as BsCions from "react-icons/bs";
import * as MdCions from "react-icons/md";
import { deleteBucket, redirectTo } from "../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";
import BucketInfo from "./BucketInfo";
import EditBucketModal from "./EditBucketModal";

const Bucket = ({ elem, index, refreshBuckets, setErrorMsg, setSuccessMsg }) => {
   const [show, setShow] = useState(false);
   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const navigate = useNavigate();
   const accessToken = localStorage.accessToken;

   return (
      <div
         onClick={() => {
            console.log(elem.bucketId);
            redirectTo(navigate, `${elem.bucketId}`, 1);
         }}
         className="col-12 col-sm-6 col-md-4 col-lg-3 p-1"
      >
         <div key={index} className="card custom-rounded bucket-hover cursor-pointer">
            <div className="card-body p-2 px-3">
               <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.name}</Tooltip>}>
                  <h5 className="w-75 card-title text-overflow">
                     <MdCions.MdOutlineStarOutline className="main-icon-align mr-2" fill="var(--gold)" />
                     {elem.name}
                  </h5>
               </OverlayTrigger>
               <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.description}</Tooltip>}>
                  <div className="text-muted text-overflow">{elem.description}</div>
               </OverlayTrigger>
            </div>
            <div>
               <Dropdown onClick={(e) => e.stopPropagation()}>
                  <Dropdown.Toggle>
                     <BsCions.BsThreeDotsVertical className="dots-icon" aria-expanded="false" />
                  </Dropdown.Toggle>
                  <Dropdown.Menu>
                     <Dropdown.Item
                        onClick={(e) => {
                           e.stopPropagation();
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

export default Bucket;
