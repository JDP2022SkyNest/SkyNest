import React, { useState } from "react";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import { Link } from "react-router-dom";
import { ProfileData } from "./ProfileData";
import "../Navbar/Navbar.css";
import "./Profile.css";
import { IconContext } from "react-icons";

const Profile = () => {
   const [sidebar, setSidebar] = useState(false);

   const showSidebar = () => setSidebar(!sidebar);

   return (
      <IconContext.Provider value={sidebar}>
         <div className="navbar ml-auto profile">
            <Link to="#" className="">
               <AiIcons.AiOutlineUser onClick={showSidebar} />
            </Link>
         </div>
         <nav className={sidebar ? "navMenuProfile active" : "navMenuProfile"}>
            <ul className="navMenuItems" onClick={showSidebar}>
               <li className="navbarToggleProfile">
                  <Link to="#" className="menuBarsSidebars">
                     <AiIcons.AiOutlineCloseCircle />
                  </Link>
               </li>
               {ProfileData.map((item, index) => {
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
export default Profile;
