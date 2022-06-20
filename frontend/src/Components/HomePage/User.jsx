import React from "react";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { openFullscreen, redirectTo } from "../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";
import ROUTES from "../Routes/ROUTES";

const User = ({ setAccessToken }) => {
   const navigate = useNavigate();

   const userLogout = () => {
      localStorage.removeItem("accessToken");
      setAccessToken("");
   };

   return (
      <DropdownButton align="end" title="User" id="dropdown-menu-align-end" variant="secondary">
         <Dropdown.Item
            onClick={() => {
               redirectTo(navigate, ROUTES.USERINFO, 1);
            }}
            eventKey="1"
         >
            Profile
         </Dropdown.Item>
         <Dropdown.Item eventKey="2">Settings</Dropdown.Item>
         <Dropdown.Item onClick={openFullscreen} eventKey="3">
            Fullscreen
         </Dropdown.Item>
         <Dropdown.Divider />
         <Dropdown.Item onClick={userLogout} eventKey="4">
            <div className="text-danger">Logout</div>
         </Dropdown.Item>
      </DropdownButton>
   );
};

export default User;
