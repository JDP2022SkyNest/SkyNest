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
      title: "File Management",
      path: "/filemanagement",
      icon: <AiIcons.AiOutlineFileSync />,
      cName: "nav-text",
   },
   {
      title: "Placeholder",
      path: "/Placeholder",
      icon: <IoIcons.IoMdPeople />,
      cName: "nav-text",
   },
   {
      title: "Placeholder",
      path: "/Placeholder",
      icon: <FaIcons.FaEnvelopeOpenText />,
      cName: "nav-text",
   },
];
