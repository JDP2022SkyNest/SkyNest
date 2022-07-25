import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import * as AiCions from "react-icons/ai";
import AxiosInstance from "../../axios/AxiosInstance";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import ModalLoader from "../../Loader/ModalLoader";

const AddFolderModal = ({ refresh, parentFolderId, bucketId }) => {
   const [show, setShow] = useState(false);
   const [name, setName] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const createNewFolder = async () => {
      try {
         await AxiosInstance.post(
            "/folders",
            {
               name,
               parentFolderId,
               bucketId: bucketId,
            },
            { headers: { Authorization: accessToken } }
         );
         setSuccessMsg("Folder Created");
         setTimeout(() => {
            setShow(false);
            setName("");
            refresh();
         }, 2000);
      } catch (err) {
         console.log(err);
         if (err.response.status === 400) {
            setErrorMsg("Inputs can't be empty");
         } else {
            setErrorMsg(err.response.data.messages);
            console.log(err);
         }
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
         <span onClick={handleShow} className="ml-1 mr-2 mr-sm-0 latte-background custom-rounded shadow">
            <AiCions.AiOutlinePlusCircle className="main-icon-align" /> Create Folder
         </span>

         <Modal show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="m-0 w-100 alert alert-danger text-danger text-center mb-3" />
               <SetSuccessMsg
                  successMsg={successMsg}
                  setSuccessMsg={setSuccessMsg}
                  customStyle="m-0 w-100 alert alert-success text-success text-center mb-3"
               />
               <form onSubmit={onFormSubmit}>
                  <fieldset disabled={loading}>
                     <div className="form-group row">
                        <label htmlFor="nameInp" className="col-sm-3 col-form-label">
                           Name:
                        </label>
                        <div className="col-sm-9">
                           <input value={name} onChange={(e) => setName(e.target.value)} className="form-control" id="nameInp" placeholder="Name" />
                        </div>
                     </div>
                     <div className="mt-4 d-flex justify-content-end">
                        {!loading ? <button className="btn btn-secondary button-width">Create</button> : <ModalLoader />}
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
                  </fieldset>
               </form>
            </Modal.Body>
         </Modal>
      </>
   );
};

export default AddFolderModal;
