import React, { useState } from "react";
import { Modal } from "react-bootstrap";
import AxiosInstance from "../../axios/AxiosInstance";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";

const InviteUser = () => {
   const [show, setShow] = useState(false);
   const [email, setEmail] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const userInvite = async () => {
      try {
         await AxiosInstance.post(
            "/invite",
            { email },
            {
               headers: { Authorization: accessToken },
            }
         );
         setSuccessMsg("User Invited");
         setTimeout(() => {
            setShow(false);
            setEmail("");
         }, 2000);
      } catch (err) {
         setErrorMsg(err.response.data.messages);
         console.error(err.response.status);
      }
   };

   const onFormSubmit = async () => {
      if (email.length < 3 || email.indexOf("@") === -1) {
         setErrorMsg("Please enter a valid email");
      } else {
         setLoading(true);
         await userInvite();
         setLoading(false);
      }
   };

   return (
      <>
         <span onClick={handleShow}>Invite User</span>

         <Modal show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="m-0 w-100 alert alert-danger text-danger text-center mb-3" />
               <SetSuccessMsg
                  successMsg={successMsg}
                  setSuccessMsg={setSuccessMsg}
                  customStyle="m-0 w-100 alert alert-success text-success text-center mb-3"
               />
               <form onSubmit={() => console.log("WWo")}>
                  <fieldset disabled={loading}>
                     <div className="form-group row">
                        <label htmlFor="emailInp" className="col-sm-3 col-form-label">
                           Email:
                        </label>
                        <div className="col-sm-9">
                           <input
                              type="email"
                              value={email}
                              onChange={(e) => setEmail(e.target.value)}
                              className="form-control"
                              id="emailInp"
                              placeholder="Email"
                              required
                           />
                        </div>
                     </div>
                     <div className="mt-4 d-flex justify-content-end">
                        <button onClick={() => onFormSubmit()} className="btn btn-secondary button-width">
                           Invite
                        </button>
                        <button
                           onClick={(e) => {
                              e.preventDefault();
                              handleClose();
                              setErrorMsg("");
                              setEmail("");
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

export default InviteUser;
