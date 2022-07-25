import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import * as AiCions from "react-icons/ai";
import AxiosInstance from "../../axios/AxiosInstance";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import ModalLoader from "../../Loader/ModalLoader";

const CreateNewTag = () => {
   const [show, setShow] = useState(false);
   const [name, setName] = useState("");
   const [rgb, setRgb] = useState("#000000");
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const createNewTag = async () => {
      if (name.length < 7) {
         try {
            await AxiosInstance.post(
               "/tags",
               { name, rgb: rgb.slice(1) },
               {
                  headers: { Authorization: accessToken },
               }
            );
            setSuccessMsg("Tag Successfully Added");
            setTimeout(() => {
               setShow(false);
               setName("");
               setRgb("#000000");
            }, 2000);
         } catch (err) {
            if (err.response.status === 400) {
               setErrorMsg("Invalid Name");
            } else {
               setErrorMsg(err.response.data.messages);
               console.log(err);
            }
         }
      } else {
         setErrorMsg("Name can't be longer than 6 characters");
      }
   };

   const onFormSubmit = async (e) => {
      e.preventDefault();
      setLoading(true);
      await createNewTag();
      setLoading(false);
   };

   return (
      <>
         <span onClick={handleShow} className="ml-auto ml-sm-2 latte-background custom-rounded shadow">
            <AiCions.AiOutlineTags className="main-icon-align" /> Create Tag
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
                     <div className="form-group row">
                        <label htmlFor="colorInp" className="col-3 col-form-label">
                           Color:
                        </label>
                        <div className="col-9">
                           <input
                              type="color"
                              value={rgb}
                              onChange={(e) => setRgb(e.target.value)}
                              className="form-control clr-input-width"
                              id="colorInp"
                           />
                        </div>
                     </div>
                     <div className="mt-4 d-flex justify-content-end">
                        {!loading ? <button className="btn btn-secondary button-width">Create</button> : <ModalLoader />}
                        <button
                           onClick={(e) => {
                              e.preventDefault();
                              handleClose();
                              setErrorMsg("");
                              setRgb("#000000");
                              setName("");
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

export default CreateNewTag;
