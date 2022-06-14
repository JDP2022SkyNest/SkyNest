import React from "react";
import { Navbar, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import { FaCaretLeft } from "react-icons/fa";

const AdminPanelNav = () => {
   const navigate = useNavigate();

   return (
      <Navbar bg="dark" variant="dark">
         <Container>
            <Navbar.Brand className="text-danger d-none d-md-block">Admin Panel</Navbar.Brand>
            <div className="form-inline my-2 my-lg-0">
               <input className="form-control mr-sm-2" placeholder="Search user" />
            </div>
            <button
               onClick={() => {
                  redirectTo(navigate, ROUTES.HOME);
               }}
               className="btn btn-secondary"
            >
               <FaCaretLeft className="icon-back-align" /> Go back
            </button>
         </Container>
      </Navbar>
   );
};

export default AdminPanelNav;
