import React from "react";

const Tag = ({ data }) => {
   const allData = data?.map((el, index) => {
      return (
         <option value={el.id} key={index}>
            {el.name}
         </option>
      );
   });

   return allData;
};

export default Tag;
