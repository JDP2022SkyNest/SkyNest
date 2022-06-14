import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import logoImage from "..//Login/assets/logoblackandwhite.svg";
import "..//Header/Header.css";
import Sidebar from "../Sidebar/Sidebar";
import Profile from "../Profile/Profile";

const Header = () => {
   return (
      <Navbar className="fixed-top d-flex" expand="lg" bg="dark" variant="dark">
         <Sidebar />
         <Nav className="me-auto">
            <Navbar.Brand href="#home">
               <img src={logoImage} alt="logo" className="logoImageBrand" />
            </Navbar.Brand>
            <Navbar.Brand href="#home" className="d-flex align-items-center">
               <h2 className="brand">SkyNest</h2>
            </Navbar.Brand>
         </Nav>
         <Profile />
      </Navbar>
   );
};

export default Header;
