import React, { useState } from "react";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import { Modal } from "react-bootstrap";
import RemoveTagPanel from "./RemoveTagPanel";

const RemoveTag = ({ TGZ, objectId, refresh }) => {
   const [show, setShow] = useState(false);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);

   return (
      <>
         <span onClick={handleShow} className="ml-0">
            Remove Tag
         </span>

         <Modal show={show} onHide={handleClose} className="mt-3" size="sm">
            <Modal.Body>
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="m-0 w-100 alert alert-danger text-danger text-center mb-3" />
               <SetSuccessMsg
                  successMsg={successMsg}
                  setSuccessMsg={setSuccessMsg}
                  customStyle="m-0 w-100 alert alert-success text-success text-center mb-3"
               />
               <RemoveTagPanel TGZ={TGZ} objectId={objectId} refresh={refresh} />
               <div className="mt-5 d-flex justify-content-end">
                  <button
                     onClick={() => {
                        refresh();
                        handleClose();
                     }}
                     className="btn btn-secondary button-width"
                  >
                     Save
                  </button>
                  <button
                     onClick={(e) => {
                        e.preventDefault();
                        handleClose();
                        setErrorMsg("");
                     }}
                     className="ml-2 btn btn-outline-secondary button-width"
                  >
                     Close
                  </button>
               </div>
            </Modal.Body>
         </Modal>
      </>
   );
};

export default RemoveTag;
