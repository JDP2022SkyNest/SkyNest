import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import LambdaAuth from "./LambdaAuth";
import LambdaConnect from "./LambdaConnect";
import * as FaIcons from "react-icons/fa";

const LambdaModal = () => {
   const [show, setShow] = useState(false);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [active, setActive] = useState(0);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);

   const allComponents = [
      { name: "Connect", comp: <LambdaConnect setErrorMsg={setErrorMsg} setActive={setActive} /> },
      { name: "Authorize", comp: <LambdaAuth setErrorMsg={setErrorMsg} setSuccessMsg={setSuccessMsg} handleClose={handleClose} /> },
   ];

   const mappedComponents = allComponents.map((el, index) => (
      <div
         key={index}
         onClick={() => setActive(index)}
         className={`${active === index && "permissions-color border-bottom"} d-inline-block py-2 px-3`}
         style={{ cursor: "pointer" }}
      >
         {el.name}
      </div>
   ));

   return (
      <>
         <span onClick={handleShow} className="ml-auto ml-sm-0 latte-background custom-rounded shadow">
            <FaIcons.FaDropbox className="main-icon-align" />
         </span>

         <Modal show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="m-0 w-100 alert alert-danger text-danger text-center mb-3" />
               <SetSuccessMsg
                  successMsg={successMsg}
                  setSuccessMsg={setSuccessMsg}
                  customStyle="m-0 w-100 alert alert-success text-success text-center mb-3"
               />
               <div className="text-dark border border-white" style={{ display: "table" }}>
                  {mappedComponents}
               </div>
               <div className="text-dark mt-3">{allComponents[active].comp}</div>
               <div className="d-flex justify-content-end mt-4">
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

export default LambdaModal;
