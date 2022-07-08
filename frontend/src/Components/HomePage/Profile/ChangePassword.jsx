import React, { useState, useEffect } from "react";
import AxiosInstance from "../../axios/AxiosInstance";
import { Modal, Form, InputGroup } from "react-bootstrap";
import * as AiIcons from "react-icons/ai";
import PasswordRequirements from "../../ReusableComponents/PasswordRequirements";
import { passwordRegEx } from "../../ReusableComponents/ReusableFunctions";
import Label from "../../ReusableComponents/Label";

const ChangePassword = ({ userID }) => {
   const [currentPassword, setCurrentPassword] = useState("");
   const [newPassword, setNewPassword] = useState("");
   const [confirmNewPw, setconfirmNewPw] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [errorMsg, setErrorMsg] = useState("");
   const [show, setShow] = useState(false);
   const [showPassword, setShowPassword] = useState(false);

   const accessToken = localStorage.accessToken;

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);

   const isSuccessfulValidation = newPassword.match(passwordRegEx) && newPassword === confirmNewPw;

   const handlePasswordChange = async () => {
      try {
         await AxiosInstance.put(
            `/users/${userID}/password-change`,
            {
               currentPassword,
               newPassword,
            },
            {
               headers: { Authorization: accessToken },
            }
         );
         setErrorMsg("");
         setSuccessMsg("Successfully changed");
         setTimeout(() => {
            setShow(false);
         }, 1000);
      } catch (err) {
         setErrorMsg(err.response.data.messages);
      }
   };

   const toggleShowHide = () => {
      setShowPassword(!showPassword);
   };

   useEffect(() => {
      setErrorMsg("");
   }, [currentPassword, newPassword, confirmNewPw]);

   return (
      <>
         <div onClick={handleShow}>
            <AiIcons.AiOutlineUnlock className="mr-2" />
            Change Password
         </div>
         <Modal show={show} onHide={handleClose} className="mt-3">
            <Modal.Header closeButton>
               <Modal.Title>Change Your Password</Modal.Title>
            </Modal.Header>
            <Modal.Body>
               <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
               <p className={successMsg ? "alert alert-success text-success text-center" : "d-none"}>{successMsg}</p>
               <Form>
                  <Form.Group className="mb-3" controlId="ControlInput1">
                     <Label>Current Password</Label>
                     <Form.Control
                        value={currentPassword}
                        onChange={(e) => setCurrentPassword(e.target.value)}
                        type={showPassword ? "text" : "password"}
                        placeholder="Current Password"
                        required
                     />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="ControlInput2">
                     <Label>New Password</Label>
                     <InputGroup>
                        <Form.Control
                           value={newPassword}
                           onChange={(e) => setNewPassword(e.target.value)}
                           type={showPassword ? "text" : "password"}
                           placeholder="New Password"
                           required
                           className="border-right-0"
                        />
                        <span onClick={toggleShowHide} className="input-group-text bg-white rounded-right border-left-0">
                           <i className={showPassword ? "fa-solid fa-eye" : "fa fa-eye"}></i>
                        </span>
                     </InputGroup>
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="ControlInput3">
                     <Label>Confirm Password</Label>
                     <Form.Control
                        value={confirmNewPw}
                        onChange={(e) => setconfirmNewPw(e.target.value)}
                        type={showPassword ? "text" : "password"}
                        placeholder="Confirm Password"
                        required
                     />
                  </Form.Group>
                  {PasswordRequirements(newPassword, confirmNewPw)}
               </Form>
               <button
                  onClick={() => {
                     if (isSuccessfulValidation) {
                        handlePasswordChange();
                     } else {
                        setErrorMsg("Requirements not met");
                     }
                  }}
                  className={isSuccessfulValidation ? "btn btn-dark btn-lg btn-block" : "btn btn-secondary btn-lg btn-block"}
                  disabled={!isSuccessfulValidation}
               >
                  Submit
               </button>
            </Modal.Body>
         </Modal>
      </>
   );
};

export default ChangePassword;
