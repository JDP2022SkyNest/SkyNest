import React from "react";
import { Navbar, Container } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";

const AdminPanelNav = ({ name, searchBar, setSearchTerm, path, children }) => {
   const navigate = useNavigate();

   return (
      <Navbar bg="dark" variant="dark">
         <Container>
            <Navbar.Brand className="text-white d-none d-md-block">{name}</Navbar.Brand>
            {searchBar && (
               <div className="form-inline my-2 my-lg-0">
                  <input onChange={(e) => setSearchTerm(e.target.value)} className="form-control mr-sm-2" placeholder="Search user" />
               </div>
            )}
            <div className="d-flex">
               <div className="mr-2 mr-sm-3 icon-modal-align">{children}</div>
               <button
                  onClick={() => {
                     redirectTo(navigate, path);
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
