import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import * as TiCions from "react-icons/ti";
import AxiosInstance from "../../axios/AxiosInstance";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";

const UploadToBucket = ({ bucketId }) => {
   const [show, setShow] = useState(false);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const fileUpload = async () => {
      try {
         await AxiosInstance.post(
            `/files/bucket/${bucketId}`,
            {
               file: "as",
            },
            { headers: { Authorization: accessToken } }
         );
         setSuccessMsg("File Successfully Uploaded");
      } catch (err) {
         setErrorMsg(err.response.data.error);
         console.log(err);
      }
   };

   const onFormSubmit = async (e) => {
      e.preventDefault();
      setLoading(true);
      await fileUpload();
      setLoading(false);
   };

   return (
      <div className="ml-1 latte-background custom-rounded">
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
                     <div className="mb-3">
                        <input className="form-control" type="file" id="formFile" />
                     </div>
                  </fieldset>
                  <button className="btn btn-secondary button-width">Upload</button>
                  <button
                     onClick={(e) => {
                        e.preventDefault();
                        handleClose();
                        setErrorMsg("");
                        setSuccessMsg("");
                     }}
                     className="ml-2 btn btn-outline-secondary button-width"
                  >
                     Close
                  </button>
               </form>
            </Modal.Body>
         </Modal>
      </div>
   );
};

export default UploadToBucket;
