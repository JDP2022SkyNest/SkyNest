import React, { useState } from "react";
import AxiosInstance from "../../axios/AxiosInstance";
import { Modal, Form } from "react-bootstrap";
import * as AiIcons from "react-icons/ai";
import PasswordRequirements from "../../ReusableComponents/PasswordRequirements";
import { passwordRegEx } from "../../ReusableComponents/ReusableFunctions";

const ChangePassword = ({ userID }) => {
   const [currentPassword, setCurrentPassword] = useState("");
   const [newPassword, setNewPassword] = useState("");
   const [confirmNewPw, setconfirmNewPw] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [show, setShow] = useState(false);
   const accessToken = localStorage.accessToken;

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);

   const isSuccessfulValidation = newPassword.match(passwordRegEx) && newPassword === confirmNewPw;

   const onPasswordChange = async () => {
      try {
         await AxiosInstance.put(
            `/users/password-change/${userID}`,
            {
               currentPassword,
               newPassword,
            },
            {
               headers: { Authorization: accessToken },
            }
         );
         setErrorMsg("");
         setSuccessMsg("Successfully changes");
      } catch (err) {
         setErrorMsg(err.response.data.messages);
         console.log(err.response.status);
      }
   };

   return (
      <>
         <div onClick={handleShow}>
            <AiIcons.AiOutlineUnlock className="mr-2" />
            Change Password
         </div>
         <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
               <Modal.Title>Change Your Password</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
               <p className={successMsg ? "alert alert-success text-success text-center" : "d-none"}>{successMsg}</p>
               <Form>
                  <Form.Group className="mb-3" controlId="ControlInput1">
                     <Form.Label>Please Enter your current Password:*</Form.Label>
                     <Form.Control
                        value={currentPassword}
                        onChange={(e) => setCurrentPassword(e.target.value)}
                        type="password"
                        placeholder="Password"
                        autoFocus
                     />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="ControlInput1">
                     <Form.Label>Please Enter new Password:*</Form.Label>
                     <Form.Control
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        type="password"
                        placeholder="Password"
                        autoFocus
                     />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="ControlInput1">
                     <Form.Label>Confirm new Password:*</Form.Label>
                     <Form.Control
                        value={confirmNewPw}
                        onChange={(e) => setconfirmNewPw(e.target.value)}
                        type="password"
                        placeholder="Password"
                        autoFocus
                     />
                  </Form.Group>
                  {PasswordRequirements(newPassword, confirmNewPw)}
               </Form>
               <button
                  onClick={() => {
                     if (isSuccessfulValidation) {
                        onPasswordChange();
                     } else {
                        setErrorMsg("Requirements not met");
                     }
                  }}
                  className={isSuccessfulValidation ? "btn btn-dark btn-lg btn-block" : "btn btn-secondary btn-lg btn-block"}
                  disabled={isSuccessfulValidation ? false : true}
               >
                  Submit
               </button>
            </Modal.Body>
         </Modal>
      </>
   );
};

export default ChangePassword;
