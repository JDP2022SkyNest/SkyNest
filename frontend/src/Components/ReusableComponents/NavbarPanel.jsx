import React from "react";
import { Navbar, Container, DropdownButton, Dropdown } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import "./NavBarPanel.css";

const AdminPanelNav = ({ name, searchBar, setSearchTerm, path, children }) => {
   const navigate = useNavigate();

   return (
      <Navbar bg="dark" variant="dark">
         <Container>
            <Navbar.Brand className="text-white d-none d-md-block">{name}</Navbar.Brand>
            {searchBar && (
               <div className="input-group" style={{ width: "200px" }}>
                  <input
                     type="text"
                     onChange={(e) => setSearchTerm(e.target.value)}
                     className="form-control"
                     aria-label="Text input with segmented dropdown button"
                     placeholder="Search User"
                  />
                  <div className="input-group-append">
                     <DropdownButton id="dropdown-basic-button" title="">
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm("admin")}>
                           Admins
                        </Dropdown.Item>
                        <Dropdown.Item className="text-dark">Disabled Users</Dropdown.Item>
                        <Dropdown.Item className="text-dark">Unverified Users</Dropdown.Item>
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm("")}>
                           Clear Search
                        </Dropdown.Item>
                     </DropdownButton>
                  </div>
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
