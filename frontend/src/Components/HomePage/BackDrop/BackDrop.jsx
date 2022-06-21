import React from "react";

import "./BackDrop.css";

const BackDrop = ({ sidebar, closeSidebar }) => {
   return <div className={sidebar ? "back-drop back-drop-open" : "back-drop"} onClick={closeSidebar}></div>;
};
export default BackDrop;
