import React, { useState } from "react";
import Footer from "../Footer/Footer";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import ROLE from "../Roles/Roles";
import { useNavigate } from "react-router-dom";
import Profile from "../HomePage/Profile/Profile";
import ToolBar from "../HomePage/ToolBar/ToolBar";
import SideBar from "../HomePage/SideBar/SideBar";
import BackDrop from "../HomePage/BackDrop/BackDrop";
import * as RiCions from "react-icons/ri";
import "./HomePage.css";
import { useContext } from "react";
import GlobalContext from "../context/GlobalContext";

const HomePage = () => {
   const navigate = useNavigate();
   const [sidebar, setSidebar] = useState(false);
   const toggleSidebar = () => {
      setSidebar((prevState) => !prevState);
   };

   const { setAccessToken, userRole, userID } = useContext(GlobalContext);

   return (
      <div className="home-page-body">
         <BackDrop sidebar={sidebar} closeSidebar={toggleSidebar} />
         <Navbar className="header py-0 bg-dark text-white">
            <Container>
               <ToolBar openSidebar={toggleSidebar} />
               <span className="d-none mr-auto d-md-block navbar-brand text-light">SkyNest</span>
               <div className="d-flex">
                  <button
                     onClick={() => {
                        redirectTo(navigate, ROUTES.ADMIN, 1);
                     }}
                     className={userRole === ROLE.ADMIN ? `btn admin mr-2` : "d-none"}
                  >
                     <RiCions.RiAdminLine className="main-icon-align" fill="var(--gold)" />
                  </button>
                  <Profile setAccessToken={setAccessToken} userRole={userRole} userID={userID} />
               </div>
            </Container>
         </Navbar>
         <SideBar sidebar={sidebar} userRole={userRole} />
         <div className="container mt-5">
            <div></div>
         </div>
         <Footer />
      </div>
   );
};

export default HomePage;
