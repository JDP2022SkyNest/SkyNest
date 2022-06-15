import React from "react";
import { DropdownButton, Dropdown } from "react-bootstrap";

const User = ({ setAccessToken }) => {
   const userLogout = () => {
      localStorage.removeItem("accessToken");
      setAccessToken("");
   };
   
   return (
      <DropdownButton align="end" title="User" id="dropdown-menu-align-end" variant="secondary">
         <Dropdown.Item eventKey="1">Profile</Dropdown.Item>
         <Dropdown.Item eventKey="2">Settings</Dropdown.Item>
         <Dropdown.Item eventKey="3">More</Dropdown.Item>
         <Dropdown.Divider />
         <Dropdown.Item onClick={userLogout} eventKey="4">
            <div className="text-danger">Logout</div>
         </Dropdown.Item>
      </DropdownButton>
   );
};

export default User;
