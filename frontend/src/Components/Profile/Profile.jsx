import React from "react";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import * as FiIcons from "react-icons/fi";
import { DropdownButton, Dropdown } from "react-bootstrap";
import "../Header/Header.css";
import "./Profile.css";

const Profile = ({ setAccessToken }) => {
   const userLogout = () => {
      localStorage.removeItem("accessToken");
      setAccessToken("");
   };
   return (
      <div className="nav-menu-profile">
         <DropdownButton align="end" title={<AiIcons.AiOutlineUser />} id="dropdown-menu-align-end" variant="dark">
            <Dropdown.Item eventKey="1">
               <CgIcons.CgProfile className="mr-2" />
               Profile
            </Dropdown.Item>
            <Dropdown.Item eventKey="2">
               <FiIcons.FiSettings className="mr-2" />
               Settings
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item onClick={userLogout} eventKey="3">
               <div>
                  <CgIcons.CgLogOut className="mr-2" />
                  Logout
               </div>
            </Dropdown.Item>
         </DropdownButton>
      </div>
   );
};
export default Profile;
