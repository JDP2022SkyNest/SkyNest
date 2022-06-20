import React from "react";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import * as FiIcons from "react-icons/fi";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { openFullscreen } from "../ReusableComponents/ReusableFunctions";

const Profile = ({ setAccessToken }) => {
   const userLogout = () => {
      localStorage.removeItem("accessToken");
      setAccessToken("");
   };

   return (
      <DropdownButton align="end" title={<AiIcons.AiOutlineUser />} id="dropdown-menu-align-end" variant="secondary">
         <Dropdown.Item eventKey="1">
            <CgIcons.CgProfile className="mr-2" />
            Profile
         </Dropdown.Item>
         <Dropdown.Item eventKey="2">
            <FiIcons.FiSettings className="mr-2" />
            Settings
         </Dropdown.Item>
         <Dropdown.Divider />
         <Dropdown.Item onClick={openFullscreen} eventKey="3">
            <AiIcons.AiOutlineFullscreen className="mr-2" />
            Fullscreen
         </Dropdown.Item>
         <Dropdown.Divider />
         <Dropdown.Item onClick={userLogout} eventKey="4">
            <div className="text-danger">
               <CgIcons.CgLogOut className="mr-2" />
               Logout
            </div>
         </Dropdown.Item>
      </DropdownButton>
   );
};

export default Profile;
