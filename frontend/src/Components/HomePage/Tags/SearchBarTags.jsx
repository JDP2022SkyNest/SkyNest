import React, { useState } from "react";
import TagZZ from "./TagZZ";
import { getAllTags } from "../../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";

const SearchBarTags = ({ setErrorMsg }) => {
   const [data, setData] = useState();
   const [menuOpen, setMenuOpen] = useState(false);
   const accessToken = localStorage.accessToken;

   const navigate = useNavigate();

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
            navigate(`/tags/${e.target.value}`, { replace: true });
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
