import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import AxiosInstance from "../axios/AxiosInstance";
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";

const EditBucketModal = ({ refreshBuckets, elem }) => {
   const [show, setShow] = useState(false);
   const [name, setName] = useState(elem.name);
   const [description, setDescription] = useState(elem.description);
   const [isPublic, setIsPublic] = useState(elem.isPublic);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const editBucket = async () => {
      try {
         await AxiosInstance.put(
            `/buckets/${elem.bucketId}`,
            {
               name,
               description,
               isPublic,
            },
            { headers: { Authorization: accessToken } }
         );
         setSuccessMsg("Bucket Eddited");
         setTimeout(() => {
            setShow(false);
            refreshBuckets();
         }, 2000);
      } catch (err) {
         if (err.response.status === 400) {
            console.log(err);
            setErrorMsg("Inputs can't be empty");
         } else {
            setErrorMsg(err.response.data.messages);
            console.log(err);
         }
      }
   };

   const onFormSubmit = async () => {
      setLoading(true);
      await editBucket();
      setLoading(false);
   };

   return (
      <>
         <div onClick={handleShow} className="text-dark">
            Edit Bucket
         </div>

         <Modal show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="m-0 w-100 alert alert-danger text-danger text-center mb-3" />
               <SetSuccessMsg
                  successMsg={successMsg}
                  setSuccessMsg={setSuccessMsg}
                  customStyle="m-0 w-100 alert alert-success text-success text-center mb-3"
               />
               <form>
                  <fieldset disabled={loading}>
                     <div className="form-group row">
                        <label htmlFor="nameInp" className="col-sm-3 col-form-label">
                           Name:
                        </label>
                        <div className="col-sm-9">
                           <input value={name} onChange={(e) => setName(e.target.value)} className="form-control" id="nameInp" placeholder="Name" />
                        </div>
                     </div>
                     <div className="form-group row">
                        <label htmlFor="descrInp" className="col-sm-3 col-form-label">
                           Description:
                        </label>
                        <div className="col-sm-9">
                           <input
                              value={description}
                              onChange={(e) => setDescription(e.target.value)}
                              className="form-control"
                              id="descrInp"
                              placeholder="Description"
                           />
                        </div>
                     </div>
                     <div className="form-group row">
                        <label htmlFor="privateInp" className="col-sm-3 col-form-label">
                           Visibility:
                        </label>
                        <div className="col-sm-9 is-private">
                           <button
                              onClick={() => {
                                 setIsPublic(false);
                              }}
                              className={`btn btn-${!isPublic ? "secondary" : "outline-white"} btn-sm`}
                           >
                              Private
                           </button>
                           <button
                              onClick={() => {
                                 setIsPublic(true);
                              }}
                              className={`btn btn-${isPublic ? "secondary" : "outline-white"} btn-sm ml-2`}
                           >
                              Public
                           </button>
                        </div>
                     </div>
                     <div className="mt-4 d-flex justify-content-end">
                        <button onClick={() => onFormSubmit()} className="btn btn-secondary button-width">
                           Edit
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
                  </fieldset>
               </form>
            </Modal.Body>
         </Modal>
      </>
   );
};

export default EditBucketModal;
