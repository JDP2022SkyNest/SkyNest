import React from "react";
import { Nav } from "react-bootstrap";
import "./Navbar.css";

import Sidebar from "../Sidebar/Sidebar";
import Profile from "../Profile/Profile";
import SearchBar from "../SearchBar/SearchBar";
import logoImage from "..//Login/assets/logoblackandwhite.svg";

const Navbar = () => {
   return (
      <Nav className="navbar navbar-expand-lg d-flex">
         <h1 className="brand">Skynest</h1>
         <img src={logoImage} alt="logo" className="logoImageBrand" />
         <Sidebar />
         <SearchBar />
         <Profile />
      </Nav>
   );
};

export default Navbar;
