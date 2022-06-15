import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import { FaQuestionCircle } from "react-icons/fa";
import { IconContext } from "react-icons/lib";

const ReusableModal = ({ title = "Default Title", children }) => {
   const [show, setShow] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);

   return (
      <>
         <div onClick={handleShow}>
            <IconContext.Provider value={{ color: "white" }}>
               <div>
                  <FaQuestionCircle />
               </div>
            </IconContext.Provider>
         </div>

         <Modal show={show} onHide={handleClose} backdrop="static" keyboard={false}>
            <Modal.Header closeButton>
               <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>{children}</Modal.Body>
            <Modal.Footer>
               <Button variant="secondary" onClick={handleClose}>
                  Close
               </Button>
            </Modal.Footer>
         </Modal>
      </>
   );
};

export default ReusableModal;
