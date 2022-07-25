import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import * as TiCions from "react-icons/ti";
import AxiosInstance from "../../axios/AxiosInstance";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import ModalLoader from "../../Loader/ModalLoader";

const UploadToBucket = ({ bucketId, refresh }) => {
   const [file, setFile] = useState(null);
   const [show, setShow] = useState(false);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const fileUpload = async () => {
      let formData = new FormData();
      formData.append("file", file);

      try {
         await AxiosInstance.post(`/files/bucket/${bucketId}`, formData, {
            headers: { Authorization: accessToken },
         });
         setSuccessMsg("File Successfully Uploaded");
         setTimeout(() => {
            setShow(false);
            setFile(null);
            refresh();
         }, 2000);
      } catch (err) {
         setErrorMsg(err.response.data.messages);
         console.log(err);
      }
   };

   const onFormSubmit = async (e) => {
      e.preventDefault();
      if (file !== null) {
         setLoading(true);
         await fileUpload();
         setLoading(false);
      } else {
         setErrorMsg("Choose a file first");
      }
   };

   return (
      <div className="ml-auto ml-sm-2 latte-background custom-rounded shadow">
         <span onClick={handleShow}>
            <TiCions.TiCloudStorageOutline className="main-icon-align" fill="var(--gold)" /> Upload To Bucket
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
                     <input onChange={(e) => setFile(e.target.files[0])} className="form-control" type="file" id="formFile" />
                  </fieldset>
                  <div className="mt-4 d-flex justify-content-end">
                     {!loading ? <button className="btn btn-secondary button-width">Upload</button> : <ModalLoader />}
                     <button
                        onClick={(e) => {
                           e.preventDefault();
                           handleClose();
                           setErrorMsg("");
                           setSuccessMsg("");
                           setFile(null);
                        }}
                        className="ml-2 btn btn-outline-secondary button-width"
                     >
                        Close
                     </button>
                  </div>
               </form>
            </Modal.Body>
         </Modal>
      </div>
   );
};

export default UploadToBucket;
