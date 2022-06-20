import React from "react";
import * as AiIcons from "react-icons/ai";
import "../HomePage/ToolBar.css";

const ToolBar = ({ openSidebar }) => {
   return (
      <div className="tool-bar">
         <div className="burger" onClick={openSidebar}>
            <AiIcons.AiOutlineMenu />
         </div>
      </div>
   );
};
export default ToolBar;
