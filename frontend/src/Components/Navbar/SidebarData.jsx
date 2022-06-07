import React from "react";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
import * as IoIcons from "react-icons/io";
import * as BiIcons from "react-icons/bi";

export const SidebarData = [
   {
      title: "Home",
      path: "/",
      icon: <AiIcons.AiFillHome />,
      cName: "nav-text",
   },
   {
      title: "Storage",
      path: "/storage",
      icon: <BiIcons.BiCloud />,
      cName: "nav-text",
   },
   {
      title: "File Manager",
      path: "/filemanager",
      icon: <AiIcons.AiOutlineFileSync />,
      cName: "nav-text",
   },
   {
      title: "Team",
      path: "/team",
      icon: <IoIcons.IoMdPeople />,
      cName: "nav-text",
   },
   {
      title: "Messages",
      path: "/messages",
      icon: <FaIcons.FaEnvelopeOpenText />,
      cName: "nav-text",
   },
];
