import React from "react";
import { Form, FormControl } from "react-bootstrap";
import "../SearchBar/SearchBar.css";

const SearchBar = () => {
   return (
      <Form className="searchBar">
         <FormControl type="search" placeholder="Search" aria-label="Search" />
      </Form>
   );
};
export default SearchBar;
