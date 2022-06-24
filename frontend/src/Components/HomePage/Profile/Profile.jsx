import React, { useState } from "react";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import { DropdownButton, Dropdown, Modal, Form, Button } from "react-bootstrap";
import { openFullscreen, redirectTo, onUserLogout } from "../../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";
import ROUTES from "../../Routes/ROUTES";
import ROLE from "..//../Roles/Roles";
import "./Profile.css";
{
   /*import { getUserData } from "../../ReusableComponents/ReusableFunctions";
import { onPasswordChange } from "../../ReusableComponents/ReusableFunctions";*/
}

const Profile = ({ setAccessToken, userRole }) => {
   const accessToken = localStorage.accessToken;
   const [show, setShow] = useState(false);
   const [oldPw, setOldPw] = useState("");
   const [newPassword, setNewPassword] = useState("");
   const [confirmPw, setConfirmPw] = useState("");

   const navigate = useNavigate();

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);

   {
      /*  const onFormSubmitPw = (e) => {
      e.preventDefault();
 onPasswordChange(userID, oldPw, password, confirmPw, accessToken);*/
   }

   return (
      <DropdownButton align="end" title={<AiIcons.AiOutlineUser />} id="dropdown-menu-align-end" variant="secondary" menuVariant="dark">
         <div className={userRole === ROLE.ADMIN ? "dropdown-menu-admin" : "dropdown-menu-worker"}>
            <Dropdown.Item
               onClick={() => {
                  redirectTo(navigate, ROUTES.USERINFO, 1);
               }}
               className="mr-2"
               eventKey="1"
            >
               <CgIcons.CgProfile className="mr-2" />
               Your Profile
            </Dropdown.Item>
            <Dropdown.Item eventKey="2" onClick={handleShow}>
               <AiIcons.AiOutlineUnlock className="mr-2" />
               Change Password
            </Dropdown.Item>
            <Modal show={show} onHide={handleClose}>
               <Modal.Header closeButton>
                  <Modal.Title>Change Your Password</Modal.Title>
               </Modal.Header>
               <Modal.Body>
                  <Form>
                     <Form.Group className="mb-3" controlId="ControlInput1">
                        <Form.Label>Please Enter your current Password:*</Form.Label>
                        <Form.Control value={oldPw} onChange={(e) => setOldPw(e.target.value)} type="password" placeholder="Password" autoFocus />
                     </Form.Group>
                     <Form.Group className="mb-3" controlId="ControlInput2">
                        <Form.Label>New Password:*</Form.Label>
                        <Form.Control value={newPassword} onChange={(e) => setNewPassword(e.target.value)} type="password" autoFocus />
                     </Form.Group>
                     <Form.Group className="mb-3" controlId="ControlInput3">
                        <Form.Label>Re-enter New Password:*</Form.Label>
                        <Form.Control value={confirmPw} onChange={(e) => setConfirmPw(e.target.value)} type="password" autoFocus />
                     </Form.Group>
                  </Form>
               </Modal.Body>
               <Modal.Footer>
                  <Button variant="secondary">Save Changes</Button>
               </Modal.Footer>
            </Modal>
            <Dropdown.Divider />
            <Dropdown.Item onClick={openFullscreen} eventKey="3">
               <AiIcons.AiOutlineFullscreen className="mr-2" />
               Fullscreen
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item
               onClick={() => {
                  onUserLogout(accessToken, setAccessToken);
               }}
               eventKey="4"
            >
               <div>
                  <CgIcons.CgLogOut className="mr-2" />
                  Logout
               </div>
            </Dropdown.Item>
         </div>
      </DropdownButton>
   );
};

export default Profile;
