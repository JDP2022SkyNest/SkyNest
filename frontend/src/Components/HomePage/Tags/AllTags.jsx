import React, { useState } from "react";
import Tag from "./Tag";
import { setTheTag, getAllTags } from "../../ReusableComponents/ReusableFunctions";

const AllTags = ({ setErrorMsg, objectId, refresh, TGZ }) => {
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
            await setTheTag(accessToken, e.target.value, objectId);
            setMenuOpen(false);
            refresh();
         }}
         defaultValue={"Default"}
         className="form-select select-width "
      >
         <option value="Tag">Tag:</option>
         <Tag data={data} TGZ={TGZ} />
      </select>
   );
};

export default AllTags;
