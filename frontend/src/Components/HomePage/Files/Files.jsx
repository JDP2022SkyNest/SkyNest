import React, { useState } from "react";
import * as AiCions from "react-icons/ai";
import * as BsCions from "react-icons/bs";
import { Dropdown, Modal } from "react-bootstrap";
import FileInfo from "./FileInfo";

const Files = ({ elem }) => {
   const [show, setShow] = useState(false);
   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   return (
      <div className="col-6 col-sm-6 col-md-3 col-lg-2 p-1 mt-2">
         <div className="cursor-pointer bg-white rounded">
            <div className="p-2 px-3">
               <div className="text-overflow">
                  <AiCions.AiOutlineFile className="main-icon-align mr-1" />
                  {elem.name}
               </div>
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
                        File Info
                     </Dropdown.Item>
                     <Dropdown.Item className="text-dark">Edit File</Dropdown.Item>
                     <Dropdown.Item className="text-dark">Download File</Dropdown.Item>
                     <Dropdown.Item className="text-dark">Delete File</Dropdown.Item>
                  </Dropdown.Menu>
               </Dropdown>
            </div>
            <Modal onClick={(e) => e.stopPropagation()} show={show} onHide={handleClose} className="mt-3">
               <Modal.Body>
                  <FileInfo elem={elem} />
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
      </div>
   );
};

export default Files;
