import React from "react";

const Tag = ({ data }) => {
   const allData = data?.map((el, index) => {
      return <div key={index}>{el.name}</div>;
   });

   return <div>{allData}</div>;
};

export default Tag;
