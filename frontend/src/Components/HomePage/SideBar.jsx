import React from "react";
import * as AiIcons from "react-icons/ai";
import * as FaIcons from "react-icons/fa";
import * as IoIcons from "react-icons/io";
import * as BiIcons from "react-icons/bi";
import "../HomePage/SideBar.css";
import Logo from "../Login/assets/logoblackandwhite.svg";

const SideBar = ({ sidebar }) => {
   return (
      <div className={sidebar ? "side-bar side-bar-open" : "side-bar"}>
         <li>
            <AiIcons.AiFillHome />
            Home
         </li>
         <li>
            <BiIcons.BiCloud />
            Storage
         </li>
         <li>
            <AiIcons.AiOutlineFileSync />
            File Management
         </li>
         <li>
            <IoIcons.IoMdPeople />
            Placeholder
         </li>
         <li>
            <FaIcons.FaEnvelopeOpenText />
            Placeholder
         </li>
         <img src={Logo} alt="logo" className="logo-sidebar" />
      </div>
   );
};
export default SideBar;
