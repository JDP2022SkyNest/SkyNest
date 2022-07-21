import React, { useState } from "react";
import AxiosInstance from "../../axios/AxiosInstance";
import Tag from "./Tag";

const AllTags = ({ setErrorMsg }) => {
   const [data, setData] = useState();
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
      <div
         onClick={(e) => {
            e.stopPropagation();
            getAllTags();
         }}
      >
         <select
            onChange={(e) => {
               console.log(e.target.value);
            }}
            defaultValue={"Default"}
            className="form-select select-width "
         >
            <option value="Tag">Tag:</option>
            <Tag data={data} />
         </select>
      </div>
   );
};

export default AllTags;
