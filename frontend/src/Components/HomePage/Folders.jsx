import React, { useState } from "react";
import { OverlayTrigger, Tooltip, Dropdown, Modal } from "react-bootstrap";
import * as BsCions from "react-icons/bs";
import * as AiCions from "react-icons/ai";
import { deleteBucket } from "../ReusableComponents/ReusableFunctions";
import EditBucketModal from "./EditBucketModal";
import FolderInfo from "./FolderInfo";

const Folders = ({ elem, index, refresh, setErrorMsg, setSuccessMsg }) => {
   const [show, setShow] = useState(false);
   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   return (
      <div key={index} className="col-12 col-sm-6 col-md-4 col-lg-3 p-1">
         <div key={index} className="card custom-rounded bucket-hover cursor-pointer">
            <div
               onClick={() => {
                  console.clear();
                  console.log(elem.bucketId);
               }}
               className="card-body p-2 px-3"
            >
               <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.name}</Tooltip>}>
                  <div className="w-75 card-title text-overflow" style={{ fontSize: "18px" }}>
                     <AiCions.AiFillFolderOpen className="main-icon-align mr-1" fill="var(--gold)" />
                     {elem.name}
                  </div>
               </OverlayTrigger>
               <OverlayTrigger overlay={<Tooltip id="tooltip-disabled">{elem.createdOn}</Tooltip>}>
                  <div className="text-muted text-overflow">{elem.createdOn}</div>
               </OverlayTrigger>
            </div>
            <div>
               <Dropdown>
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
                        Folder Info
                     </Dropdown.Item>
                     <Dropdown.Item className="text-dark">
                        <EditBucketModal refresh={refresh} elem={elem} />
                     </Dropdown.Item>
                     <Dropdown.Item
                        onClick={async () => {
                           await deleteBucket(accessToken, elem.bucketId, setErrorMsg, setSuccessMsg);
                           refresh();
                        }}
                        className="text-dark"
                     >
                        Delete bucket
                     </Dropdown.Item>
                  </Dropdown.Menu>
               </Dropdown>
            </div>
         </div>
         <Modal onClick={(e) => e.stopPropagation()} show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <FolderInfo elem={elem} />
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

export default Folders;
