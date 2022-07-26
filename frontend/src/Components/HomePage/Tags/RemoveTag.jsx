import React, { useState } from "react";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import { Modal } from "react-bootstrap";
import RemoveTagPanel from "./RemoveTagPanel";
import ModalLoader from "../../Loader/ModalLoader";

const RemoveTag = ({ TGZ, objectId, refresh }) => {
   const [show, setShow] = useState(false);
   const [errorMsg, setErrorMsg] = useState("");
   const [loading, setLoading] = useState(false);

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
               <RemoveTagPanel
                  TGZ={TGZ}
                  objectId={objectId}
                  refresh={refresh}
                  setErrorMsg={setErrorMsg}
                  handleClose={handleClose}
                  setLoading={setLoading}
               />
               <div className="mt-5 d-flex justify-content-end">
                  {!loading ? (
                     <button
                        onClick={(e) => {
                           e.preventDefault();
                           handleClose();
                           setErrorMsg("");
                        }}
                        className="ml-2 btn btn-secondary button-width"
                     >
                        Close
                     </button>
                  ) : (
                     <ModalLoader />
                  )}
               </div>
            </Modal.Body>
         </Modal>
      </>
   );
};

export default RemoveTag;
