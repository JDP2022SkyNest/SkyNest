import React, { useState } from "react";
import TagZZ from "./TagZZ";
import { getAllTags, getAllSpecificTags } from "../../ReusableComponents/ReusableFunctions";

const SearchBarTags = ({ setErrorMsg }) => {
   const [data, setData] = useState();
   const [menuOpen, setMenuOpen] = useState(false);
   const accessToken = localStorage.accessToken;

   return (
      <select
         onClick={(e) => {
            if (!menuOpen) {
               e.stopPropagation();
            }
            setMenuOpen(!menuOpen);
            getAllTags(accessToken, setData, setErrorMsg);
         }}
         onChange={async (e) => {
            getAllSpecificTags(accessToken, e.target.value);
         }}
         defaultValue={"Default"}
         className="form-select select-width "
      >
         <option value="Tag">Tag:</option>
         <TagZZ data={data} />
      </select>
   );
};

export default SearchBarTags;
