import React from "react";
import { Nav } from "react-bootstrap";
import "./Navbar.css";
import Sidebar from "../Navbar/Sidebar";
import Profile from "../Navbar/Profile";
import logoImage from "..//Login/assets/logoblackandwhite.svg";

function Navbar() {
   return (
      <Nav className="d-flex">
         <h1 className="brand">Skynest</h1>
         <img src={logoImage} alt="logo" className="logoImageBrand" />
         <Sidebar />
         <Profile />
      </Nav>
   );
}

export default Navbar;
