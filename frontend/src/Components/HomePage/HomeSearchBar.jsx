import React from "react";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import "../ReusableComponents/NavBarPanel.css";

const HomeSearchBar = ({ searchBar, setSearchTerm, searchTerm, homeSearch = false, setDelState, placeholder = "Search User" }) => {
   const navigate = useNavigate();

   const searchFilter = () => {
      if (searchTerm === false) {
         return "Disabled & Unverified";
      } else if (searchTerm === true) {
         return "Active Users";
      }
      return searchTerm;
   };

   return (
      <div className="ml-0 margin-left-home-search">
         {searchBar && (
            <div className="input-group" style={{ width: "200px" }}>
               <input
                  type="text"
                  value={searchFilter()}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="form-control"
                  aria-label="Text input with segmented dropdown button"
                  placeholder={placeholder}
               />
               {!homeSearch ? (
                  <div className="input-group-append">
                     <DropdownButton id="dropdown-basic-button" align={"end"} title="">
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm("admin")}>
                           Admins
                        </Dropdown.Item>
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm("manager")}>
                           Managers
                        </Dropdown.Item>
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm("worker")}>
                           Workers
                        </Dropdown.Item>
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm(true)}>
                           Active Users
                        </Dropdown.Item>
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm(false)}>
                           Disabled & Unverified
                        </Dropdown.Item>
                        <Dropdown.Item className="text-dark" onClick={() => setSearchTerm("")}>
                           Clear Search
                        </Dropdown.Item>
                     </DropdownButton>
                  </div>
               ) : (
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
               )}
            </div>
         )}
      </div>
   );
};

export default HomeSearchBar;
