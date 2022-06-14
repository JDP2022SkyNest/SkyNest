import React from "react";
import { Navbar, Nav, Form, FormControl, Button } from "react-bootstrap";
import logoImage from "..//Login/assets/logoblackandwhite.svg";
import "./Navbar.css";
import Sidebar from "../Sidebar/Sidebar";
import Profile from "../Profile/Profile";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";
import ROUTES from "../Routes/ROUTES";

const Header = () => {
   const navigate = useNavigate();

   return (
      <Navbar expand="lg" bg="dark" variant="dark">
         <Sidebar />
         <Navbar.Toggle aria-controls="responsive-navbar-nav d-none" />

         <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
               <Navbar.Brand href="#home">
                  <img src={logoImage} alt="logo" className="logoImageBrand" />
               </Navbar.Brand>
               <Navbar.Brand href="#home" className=" d-flex align-items-center">
                  <h2 className="brand">SkyNest</h2>
               </Navbar.Brand>
            </Nav>
            <Form className="d-flex">
               <FormControl type="search" placeholder="Search" className="me-2" aria-label="Search" />
               <Button variant="outline-secondary">Search</Button>
            </Form>
            <Button
               onClick={() => {
                  redirectTo(navigate, ROUTES.ADMIN, 1);
               }}
               className="ml-3"
               variant="outline-danger"
            >
               Admin Panel
            </Button>
         </Navbar.Collapse>
         <Profile />
      </Navbar>
   );
};

export default Header;
