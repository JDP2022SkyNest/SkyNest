import React from "react";
import "..//HomePage/HomePage.css";
import Navbar from "../Navbar/Navbar";
import Footer from "../Footer/Footer";
import CenteredContainer from "..//ReusableComponents/CenteredContainer";

const HomePage = () => {
   return (
      <>
         <Navbar />
         <CenteredContainer>HOMEPAGE</CenteredContainer>
         <Footer />
      </>
   );
};

export default HomePage;
