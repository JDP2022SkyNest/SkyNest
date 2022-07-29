import React from "react";

const TagZZ = ({ data }) => {
   const allData = data?.map((el, index) => {
      return (
         <option value={el.id} key={index}>
            {el.name}
         </option>
      );
   });

   return allData;
};

export default TagZZ;
