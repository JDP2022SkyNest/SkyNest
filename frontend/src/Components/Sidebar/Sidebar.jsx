import React, { useState } from "react";
import * as AiIcons from "react-icons/ai";
import { Link } from "react-router-dom";
import { SidebarData } from "./SidebarData";
import "../Navbar/Navbar.css";
import { IconContext } from "react-icons";

const Sidebar = () => {
   const [sidebar, setSidebar] = useState(false);

   const showSidebar = () => setSidebar(!sidebar);
   return (
      <IconContext.Provider value={sidebar}>
         <div className="navbar">
            <Link to="#" className="menuBars">
               <AiIcons.AiOutlineMenu onClick={showSidebar} />
            </Link>
         </div>
         <nav className={sidebar ? "navMenu active" : "navMenu"}>
            <ul className="navMenuItems" onClick={showSidebar}>
               <li className="navbarToggle">
                  <Link to="#" className="menuBarsSidebars">
                     <AiIcons.AiOutlineCloseCircle />
                  </Link>
               </li>

               {SidebarData.map((item, index) => {
                  return (
                     <li key={index} className={item.cName}>
                        <Link to={item.path}>
                           {item.icon}
                           <span>{item.title}</span>
                        </Link>
                     </li>
                  );
               })}
            </ul>
         </nav>
      </IconContext.Provider>
   );
};

export default Sidebar;
