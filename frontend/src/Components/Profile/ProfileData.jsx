import React from "react";
import * as CgIcons from "react-icons/cg";
import * as FiIcons from "react-icons/fi";

export const ProfileData = [
   {
      title: "Your profile",
      path: "/",
      icon: <CgIcons.CgProfile />,
      cName: "nav-text",
   },
   {
      title: "Settings",
      path: "/",
      icon: <FiIcons.FiSettings />,
      cName: "nav-text",
   },
   {
      title: "Logout",
      path: "/",
      icon: <CgIcons.CgLogOut />,
      cName: "nav-text",
      onclick: alert("Hello, How are you?"),
   },
];
