import React from "react";
import { Route } from "react-router-dom";
import { Nav } from "react-bootstrap";
import "./Navbar.css";
import Sidebar from "../Navbar/Sidebar";
import Profile from "../Navbar/Profile";
import YourProfile from "../pages/YourProfile";
import Settings from "../pages/Settings";
import Logout from "../pages/Logout";
import logoImage from "..//Login/assets/logoblackandwhite.svg";

function Navbar() {
   return (
      <Nav className="d-flex ">
         <h1 className="brand">Skynest</h1>
         <img src={logoImage} alt="logo" className="logoImageBrand" />
         <Sidebar />
         <Profile>
            <Route to="/yourprofile" component={YourProfile} />
            <Route path="/settings" component={Settings} />
            <Route path="/logout" component={Logout} />
         </Profile>
      </Nav>
   );
}

export default Navbar;
