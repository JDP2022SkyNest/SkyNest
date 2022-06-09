import React from "react";
import * as CgIcons from "react-icons/cg";
import * as FiIcons from "react-icons/fi";

export const ProfileData = [
   {
      title: "Your profile",
      path: "/yourprofile",
      icon: <CgIcons.CgProfile />,
      cName: "nav-text",
   },
   {
      title: "Settings",
      path: "/settings",
      icon: <FiIcons.FiSettings />,
      cName: "nav-text",
   },
   {
      title: "Logout",
      path: "/logout",
      icon: <CgIcons.CgLogOut />,
      cName: "nav-text",
   },
];
