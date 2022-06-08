import React, { useState } from "react";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import { Link } from "react-router-dom";
import { ProfileData } from "./ProfileData";
import "./Navbar.css";
import "./Profile.css";
import { IconContext } from "react-icons";

const Profile = () => {
   const [sidebar, setSidebar] = useState(false);

   const showSidebar = () => setSidebar(!sidebar);
   return (
      <IconContext.Provider className="" value={sidebar ? { color: "fff" } : { color: "000" }}>
         <div className="navbar ml-auto profile p-2">
            <Link to="#" className="ml-auto profile p-2">
               <CgIcons.CgProfile onClick={showSidebar} />
            </Link>
         </div>
         <nav className={sidebar ? "nav-menu-profile active" : "nav-menu-profile"}>
            <ul className="nav-menu-items" onClick={showSidebar}>
               <li className="navbar-toggle">
                  <Link to="#" className="menu-bars">
                     <AiIcons.AiOutlineClose />
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
