import React, { useState, useContext } from "react";
import { Dropdown, Modal } from "react-bootstrap";
import * as BsCions from "react-icons/bs";
import * as AiCions from "react-icons/ai";
import { deleteFolder, restoreFolder } from "../../ReusableComponents/ReusableFunctions";
import EditFolderModal from "./EditFolderModal";
import FolderInfo from "./FolderInfo";
import { useNavigate } from "react-router-dom";
import AllTags from "../Tags/AllTags";
import TagDisplay from "../Tags/TagDisplay";
import GlobalContext from "../../context/GlobalContext";
import RemoveTag from "../Tags/RemoveTag";
import FolderPermissionModal from "./Permissions/FolderPermissionModal";

const Folders = ({ elem, setErrorMsg, setSuccessMsg, refresh }) => {
   const [show, setShow] = useState(false);
   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const navigate = useNavigate();
   const accessToken = localStorage.accessToken;

   const TGZ = elem?.tags;
   const timeFrame = elem.createdOn.replace("T", " @ ");

   const { moveFolderID, setMoveFilderID, setMoveFileID } = useContext(GlobalContext);

   const writeTags = TGZ?.map((el, index) => {
      return <TagDisplay key={index} el={el} />;
   });

   return (
      <div className="col-12 col-sm-6 col-md-4 col-lg-3 p-1">
         <div className={`card custom-rounded bucket-hover cursor-pointer border-0 shadow ${elem.deletedOn !== null && "deleted-clr"}`}>
            <div
               onClick={() => {
                  navigate(`/folder/${elem.id}`, { replace: true });
               }}
               className="card-body p-2 px-3"
            >
               <div className="w-75 card-title text-overflow" style={{ fontSize: "18px" }}>
                  <AiCions.AiFillFolderOpen className="main-icon-align mr-1" fill="var(--gold)" />
                  {elem.name}
               </div>
               <div className="text-muted text-overflow">{timeFrame}</div>
               <div className="w-100  text-overflow">
                  <small>
                     <AiCions.AiOutlineTag className="main-icon-align" />
                     {elem?.tags?.length > 0 ? <span className="ml-1">{writeTags}</span> : <span className="ml-1 text-muted">No tags</span>}
                  </small>
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
                        Folder Info
                     </Dropdown.Item>
                     {elem.deletedOn === null && (
                        <Dropdown.Item className="text-dark">
                           <EditFolderModal elem={elem} refresh={refresh} />
                        </Dropdown.Item>
                     )}
                     {elem.deletedOn === null && (
                        <Dropdown.Item
                           onClick={() => {
                              if (elem.id === moveFolderID) {
                                 setMoveFilderID("");
                              } else {
                                 setMoveFilderID(elem.id);
                                 setMoveFileID("");
                              }
                           }}
                           className="text-dark"
                        >
                           {elem.id === moveFolderID ? "Cancel Move" : "Move Folder"}
                        </Dropdown.Item>
                     )}
                     {elem.deletedOn === null && (
                        <Dropdown.Item className="text-dark">
                           <FolderPermissionModal objectId={elem.id} />
                        </Dropdown.Item>
                     )}
                     {elem.deletedOn === null && (
                        <Dropdown.Item className="text-dark">
                           <AllTags refresh={refresh} objectId={elem.id} TGZ={TGZ} setErrorMsg={setErrorMsg} />
                        </Dropdown.Item>
                     )}
                     {elem.deletedOn === null && TGZ.length > 0 ? (
                        <Dropdown.Item className="text-dark">
                           <RemoveTag TGZ={TGZ} objectId={elem.id} refresh={refresh} />
                        </Dropdown.Item>
                     ) : null}
                     {elem.deletedOn === null ? (
                        <Dropdown.Item
                           onClick={async () => {
                              await deleteFolder(accessToken, elem.id, setErrorMsg, setSuccessMsg);
                              refresh();
                           }}
                           className="text-danger"
                        >
                           Delete folder
                        </Dropdown.Item>
                     ) : (
                        <Dropdown.Item
                           onClick={async () => {
                              await restoreFolder(accessToken, elem.id, setErrorMsg, setSuccessMsg);
                              refresh();
                           }}
                           className="text-danger"
                        >
                           Restore folder
                        </Dropdown.Item>
                     )}
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
