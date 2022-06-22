import React from "react";
import * as AiIcons from "react-icons/ai";
import "./ToolBar.css";

const ToolBar = ({ openSidebar }) => {
   return (
      <div className="tool-bar">
         <div className="burger" onClick={openSidebar}>
            <AiIcons.AiOutlineMenu className="burger-icon" />
         </div>
      </div>
   );
};
export default ToolBar;
