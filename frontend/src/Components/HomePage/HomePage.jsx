import React from "react";
import "..//HomePage/HomePage.css";
import Footer from "../Footer/Footer";
import Navbar from "../Navbar/Navbar";
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
