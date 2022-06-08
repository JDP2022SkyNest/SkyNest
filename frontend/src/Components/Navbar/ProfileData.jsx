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
      path: "/Settings",
      icon: <FiIcons.FiSettings />,
      cName: "nav-text",
   },
   {
      title: "Logout",
      path: "/Log out",
      icon: <CgIcons.CgLogOut />,
      cName: "nav-text",
   },
];
