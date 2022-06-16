import React from "react";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import { useNavigate } from "react-router-dom";
import logoImage from "..//Login/assets/logoblackandwhite.svg";
import "..//Header/Header.css";
import Sidebar from "../Sidebar/Sidebar";
import Profile from "../Profile/Profile";

const Header = ({ setAccessToken }) => {
   const navigate = useNavigate();
   return (
      <Navbar bg="dark" variant="dark">
         <Container>
            <Sidebar />
            <Navbar.Brand href="#home" className="d-flex align-items-center me-auto">
               <img src={logoImage} alt="logo" className="logo-image-brand" />
               <h3 className="brand">SkyNest</h3>
            </Navbar.Brand>
            <div className="d-flex">
               <button
                  onClick={() => {
                     redirectTo(navigate, ROUTES.ADMIN, 1);
                  }}
                  className="btn btn-danger mr-3"
               >
                  Admin Panel
               </button>
               <Profile setAccessToken={setAccessToken} />
            </div>
         </Container>
      </Navbar>
   );
};

export default Header;
