import React, { useState } from "react";
import * as AiIcons from "react-icons/ai";
import * as FaIcons from "react-icons/fa";
import * as IoIcons from "react-icons/io";
import * as BiIcons from "react-icons/bi";
import { Link } from "react-router-dom";
import "../Header/Header.css";
import "../Sidebar/Sidebar.css";
import { IconContext } from "react-icons";

const Sidebar = () => {
   const [sidebar, setSidebar] = useState(false);

   const showSidebar = () => setSidebar(!sidebar);
   return (
      <IconContext.Provider value={sidebar}>
         <div className="navbar">
            <Link to="#" className="menu-bars">
               <AiIcons.AiOutlineMenu onClick={showSidebar} />
            </Link>
         </div>
         <nav className={sidebar ? "nav-menu active" : "nav-menu"}>
            <ul className="navMenuItems" onClick={showSidebar}>
               <li className="navbarToggle">
                  <Link to="#" className="menu-bars-sidebars">
                     <AiIcons.AiOutlineCloseCircle />
                  </Link>
               </li>

               <li className="nav-text">
                  <Link to="/">
                     <AiIcons.AiFillHome />
                     <span>Home</span>
                  </Link>
               </li>
               <li className="nav-text">
                  <Link to="/">
                     <BiIcons.BiCloud />
                     <span>Storage</span>
                  </Link>
               </li>
               <li className="nav-text">
                  <Link to="/">
                     <AiIcons.AiOutlineFileSync />
                     <span>File Management</span>
                  </Link>
               </li>
               <li className="nav-text">
                  <Link to="/">
                     <IoIcons.IoMdPeople />
                     <span>Placeholder</span>
                  </Link>
               </li>
               <li className="nav-text">
                  <Link to="/">
                     <FaIcons.FaEnvelopeOpenText />
                     <span>Placeholder</span>
                  </Link>
               </li>
            </ul>
         </nav>
      </IconContext.Provider>
   );
};

export default Sidebar;
