import React from "react";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { openFullscreen, redirectTo, onUserLogout } from "../../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";
import ChangePassword from "./ChangePassword";
import ROUTES from "../../Routes/ROUTES";
import ROLE from "..//../Roles/Roles";
import "./Profile.css";

const Profile = ({ setAccessToken, userRole, userID }) => {
   const accessToken = localStorage.accessToken;

   const navigate = useNavigate();

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
            <Dropdown.Item eventKey="2">
               <ChangePassword userID={userID} />
            </Dropdown.Item>
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
