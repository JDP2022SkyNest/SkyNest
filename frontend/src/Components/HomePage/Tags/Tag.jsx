import React from "react";

const Tag = ({ data, TGZ }) => {
   const allT = TGZ?.map((tag) => {
      return tag.id;
   });

   const allData = data?.map((el, index) => {
      if (allT.includes(el.id)) {
         return (
            <option value={el.id} key={index} style={{ color: "var(--gold)" }}>
               {el.name}
            </option>
         );
      }
      return (
         <option value={el.id} key={index}>
            {el.name}
         </option>
      );
   });

   return allData;
};

export default Tag;
