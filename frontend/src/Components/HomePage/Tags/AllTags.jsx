import React, { useState } from "react";
import AxiosInstance from "../../axios/AxiosInstance";
import Tag from "./Tag";
import { setTheTag } from "../../ReusableComponents/ReusableFunctions";

const AllTags = ({ setErrorMsg, objectId, refresh }) => {
   const [data, setData] = useState();
   const [menuOpen, setMenuOpen] = useState(false);
   const accessToken = localStorage.accessToken;

   const getAllTags = async () => {
      try {
         const response = await AxiosInstance.get("/tags", {
            headers: { Authorization: accessToken },
         });
         setData(response.data);
      } catch (err) {
         setErrorMsg(err.response.data.messages);
         console.log(err);
      }
   };

   return (
      <select
         onClick={(e) => {
            if (!menuOpen) {
               e.stopPropagation();
               setMenuOpen(true);
            }
            getAllTags();
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
         <Tag data={data} />
      </select>
   );
};

export default AllTags;
