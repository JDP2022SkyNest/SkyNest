import React from "react";

const TagDisplay = ({ el }) => {
   return (
      <span className="mr-1 badge tag-align" style={{ backgroundColor: `#${el.rgb}`, fontSize: "9px" }}>
         {el.name}
      </span>
   );
};

export default TagDisplay;
