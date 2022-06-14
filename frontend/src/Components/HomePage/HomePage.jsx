import React from "react";
import "..//HomePage/HomePage.css";
import Footer from "../Footer/Footer";
import Header from "../Navbar/Navbar";
import CenteredContainer from "..//ReusableComponents/CenteredContainer";

const HomePage = () => {
   return (
      <>
         <Header />
         <CenteredContainer>HOMEPAGE</CenteredContainer>
         <Footer />
      </>
   );
};

export default HomePage;
