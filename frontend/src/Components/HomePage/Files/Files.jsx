import React, { useState, useContext } from "react";
import * as AiCions from "react-icons/ai";
import * as BsCions from "react-icons/bs";
import { Dropdown, Modal } from "react-bootstrap";
import FileInfo from "./FileInfo";
import { fileDownload, deleteFile } from "../../ReusableComponents/ReusableFunctions";
import EditFileInfo from "./EditFileInfo";
import GlobalContext from "../../context/GlobalContext";

const Files = ({ elem, setErrorMsg, setSuccessMsg, setInfoMsg, refresh }) => {
   const [show, setShow] = useState(false);
   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const { moveFileID, setMoveFileID, setMoveFilderID } = useContext(GlobalContext);

   return (
      <div className="col-6 col-sm-6 col-md-3 col-lg-2 p-1 mt-2">
         <div className={`cursor-pointer bucket-hover rounded shadow ${elem.deletedOn !== null ? "deleted-clr" : "bg-white"}`}>
            <div className="p-2 px-3">
               <div className="text-overflow file-text-width">
                  <AiCions.AiOutlineFile className="main-icon-align mr-1" fill="var(--gold)" />
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
                           handleShow();
                        }}
                        className="text-dark"
                     >
                        File Info
                     </Dropdown.Item>
                     <Dropdown.Item className="text-dark">
                        <EditFileInfo elem={elem} refresh={refresh} />
                     </Dropdown.Item>
                     <Dropdown.Item
                        onClick={() => {
                           if (elem.id === moveFileID) {
                              setMoveFileID("");
                           } else {
                              setMoveFileID(elem.id);
                              setMoveFilderID("");
                           }
                        }}
                        className="text-dark"
                     >
                        {elem.id === moveFileID ? "Cancel Move" : "Move File"}
                     </Dropdown.Item>
                     <Dropdown.Item
                        onClick={async () => {
                           setInfoMsg("Preparing Download");
                           await fileDownload(accessToken, elem.id, elem.name, setErrorMsg, setSuccessMsg);
                           setInfoMsg("");
                        }}
                        className="text-dark"
                     >
                        Download File
                     </Dropdown.Item>
                     <Dropdown.Item
                        onClick={async () => {
                           await deleteFile(accessToken, elem.id, setErrorMsg, setSuccessMsg);
                           refresh();
                        }}
                        className="text-danger"
                     >
                        Delete File
                     </Dropdown.Item>
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
