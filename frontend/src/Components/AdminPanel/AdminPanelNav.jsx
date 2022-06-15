import React from "react";
import { Navbar, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import ReusableModal from "../ReusableComponents/ReusableModal";
import AdminCarousel from "./AdminCarousel";

const AdminPanelNav = ({ setSearchTerm }) => {
   const navigate = useNavigate();

   return (
      <Navbar bg="dark" variant="dark">
         <Container>
            <Navbar.Brand className="text-white d-none d-md-block">Admin Panel</Navbar.Brand>
            <div className="form-inline my-2 my-lg-0">
               <input onChange={(e) => setSearchTerm(e.target.value)} className="form-control mr-sm-2" placeholder="Search user" />
            </div>

            <div className="d-flex">
               <div className="mr-1 mr-sm-3 icon-modal-align">
                  <ReusableModal title="Instructions">
                     <AdminCarousel />
                  </ReusableModal>
               </div>
               <button
                  onClick={() => {
                     redirectTo(navigate, ROUTES.HOME);
                  }}
                  className="btn btn-secondary"
               >
                  Go back
               </button>
            </div>
         </Container>
      </Navbar>
   );
};

export default AdminPanelNav;
