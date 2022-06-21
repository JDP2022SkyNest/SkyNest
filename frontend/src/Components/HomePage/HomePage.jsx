import React, { useState } from "react";
import Footer from "../Footer/Footer";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import { useNavigate } from "react-router-dom";
import Profile from "../HomePage/Profile/Profile";
import ToolBar from "../HomePage/ToolBar/ToolBar";
import SideBar from "../HomePage/SideBar/SideBar";
import BackDrop from "../HomePage/BackDrop/BackDrop";
import "./HomePage.css";

const HomePage = ({ setAccessToken }) => {
   const navigate = useNavigate();
   const [sidebar, setSidebar] = useState(false);
   const toggleSidebar = () => {
      setSidebar((prevState) => !prevState);
   };

   return (
      <>
         <BackDrop sidebar={sidebar} closeSidebar={toggleSidebar} />
         <Navbar className="header">
            <Container>
               <ToolBar openSidebar={toggleSidebar} />
               <span className="mr-auto d-none d-md-block tool-bar">SkyNest </span>
               <div className="d-flex">
                  <button
                     onClick={() => {
                        redirectTo(navigate, ROUTES.ADMIN, 1);
                     }}
                     className="btn mr-3 admin"
                  >
                     Admin Panel
                  </button>
                  <Profile setAccessToken={setAccessToken} />
               </div>
            </Container>
         </Navbar>
         <SideBar sidebar={sidebar} />
         <div className="container mt-5">
            <div>
               Lorem, ipsum dolor sit amet consectetur adipisicing elit. Corporis fuga nesciunt quo, adipisci laborum ut exercitationem officia sint
               illum, quasi molestias neque, earum incidunt voluptate eum iusto aperiam. Pariatur, impedit.
            </div>
         </div>
         <Footer />
      </>
   );
};

export default HomePage;
