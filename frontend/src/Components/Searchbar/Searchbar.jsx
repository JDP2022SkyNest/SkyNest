import React from "react";
import "..//Searchbar/searchBar.css";

const SearchBar = () => {
   return (
      <div className="form-inline ">
         <input className="form-control searchNavbar" id="searchInput" type="search" placeholder="Search" aria-label="Search"></input>
      </div>
   );
};

export default SearchBar;
