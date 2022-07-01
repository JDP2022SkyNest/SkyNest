import React from "react";
import "./Footer.css";
import { openFullscreen } from "../ReusableComponents/ReusableFunctions";

const Footer = () => {
   return (
      <footer className="footer">
         <div onClick={openFullscreen} className="footer-text py-2">
            © 2022 Copyright SkyNest
         </div>
      </footer>
   );
};
export default Footer;
