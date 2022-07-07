import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import * as AiCions from "react-icons/ai";
import AxiosInstance from "../axios/AxiosInstance";
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";

const AddFolderModal = () => {
   const [show, setShow] = useState(false);
   const [name, setName] = useState("");
   const [description, setDescription] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const createNewFolder = async () => {
      console.log(accessToken);
      try {
         await AxiosInstance.post(
            "/buckets",
            {
               name,
               description,
            },
            { headers: { Authorization: accessToken } }
         );
         setSuccessMsg("Folder Created");
         setTimeout(() => {
            setShow(false);
            setName("");
            setDescription("");
         }, 2000);
      } catch (err) {
         setErrorMsg("Error while creating a folder");
         console.log(err);
      }
   };

   const onFormSubmit = async (e) => {
      e.preventDefault();
      setLoading(true);
      await createNewFolder();
      setLoading(false);
   };

   return (
      <>
         <span onClick={handleShow} className="latte-background">
            <AiCions.AiOutlinePlusCircle className="main-icon-align" /> Add Folder
         </span>

         <Modal show={show} onHide={handleClose}>
            <Modal.Body>
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="m-0 w-100 alert alert-danger text-danger text-center mb-3" />
               <SetSuccessMsg
                  successMsg={successMsg}
                  setSuccessMsg={setSuccessMsg}
                  customStyle="m-0 w-100 alert alert-danger text-danger text-center mb-3"
               />
               <form onSubmit={onFormSubmit}>
                  <fieldset disabled={loading}>
                     <div class="form-group row">
                        <label for="nameInp" class="col-sm-3 col-form-label">
                           Name:
                        </label>
                        <div class="col-sm-9">
                           <input value={name} onChange={(e) => setName(e.target.value)} class="form-control" id="nameInp" placeholder="Name" />
                        </div>
                     </div>
                     <div class="form-group row">
                        <label for="descrInp" class="col-sm-3 col-form-label">
                           Description:
                        </label>
                        <div class="col-sm-9">
                           <input
                              value={description}
                              onChange={(e) => setDescription(e.target.value)}
                              class="form-control"
                              id="descrInp"
                              placeholder="Description"
                           />
                        </div>
                     </div>
                     <div className="mt-4 d-flex justify-content-end">
                        <button className="btn btn-info">Create</button>
                        <button
                           onClick={(e) => {
                              e.preventDefault();
                              handleClose();
                              setErrorMsg("");
                           }}
                           className="ml-2 btn btn-secondary"
                        >
                           Close
                        </button>
                     </div>
                  </fieldset>
               </form>
            </Modal.Body>
         </Modal>
      </>
   );
};

export default AddFolderModal;
