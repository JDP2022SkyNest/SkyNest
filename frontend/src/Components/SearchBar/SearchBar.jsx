import React from "react";
import "../SearchBar/SearchBar.css";

const SearchBar = () => {
   return (
      <nav className="searchNavbar searchBar mt-1 pe-md-5">
         <span className="navbar-brand mb-0 h1 ms-5 me-5"></span>
         <div className="form-inline ms-5 me-5">
            <input className="form-control mr-sm-2" id="searchInput" type="search" placeholder="Search" aria-label="Search"></input>
         </div>
      </nav>
   );
};
export default SearchBar;
