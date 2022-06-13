import React from "react";
import { Nav } from "react-bootstrap";
import "./Navbar.css";

import Sidebar from "../Sidebar/Sidebar";
import Profile from "../Profile/Profile";
import logoImage from "..//Login/assets/logoblackandwhite.svg";

const Navbar = () => {
   return (
      <Nav className="navbar d-flex">
         <h1 className="brand">Skynest</h1>
         <img src={logoImage} alt="logo" className="logoImageBrand" />
         <Sidebar />
         <Profile />
      </Nav>
   );
};

export default Navbar;
