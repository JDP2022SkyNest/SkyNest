import React from "react";
import { DropdownButton, Dropdown } from "react-bootstrap";
import "../ReusableComponents/NavBarPanel.css";

const HomeSearchBar = ({ searchBar, setSearchTerm, searchTerm, setDelState, placeholder = "Search User" }) => {
   return (
      <div>
         {searchBar && (
            <div className="input-group" style={{ width: "200px" }}>
               <input
                  type="text"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="form-control"
                  aria-label="Text input with segmented dropdown button"
                  placeholder={placeholder}
               />
               <div className="input-group-append">
                  <DropdownButton id="dropdown-basic-button" align={"end"} title="">
                     <Dropdown.Item className="text-dark" onClick={() => setDelState(true)}>
                        Deleted
                     </Dropdown.Item>
                     <Dropdown.Item className="text-dark" onClick={() => setDelState(false)}>
                        Not Deleted
                     </Dropdown.Item>
                     <Dropdown.Item className="text-dark" onClick={() => setSearchTerm("")}>
                        Clear Search
                     </Dropdown.Item>
                  </DropdownButton>
               </div>
            </div>
         )}
      </div>
   );
};

export default HomeSearchBar;
