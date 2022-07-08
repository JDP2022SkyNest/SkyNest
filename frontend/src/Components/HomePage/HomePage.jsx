import React, { useState } from "react";
import Footer from "../Footer/Footer";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo, getAllBuckets } from "../ReusableComponents/ReusableFunctions";
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
import AddFolderModal from "./AddFolderModal";
import { useEffect } from "react";
import Folder from "./Folder";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";

const HomePage = () => {
   const navigate = useNavigate();
   const [sidebar, setSidebar] = useState(false);
   const [allFolders, setAllFolders] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const toggleSidebar = () => {
      setSidebar((prevState) => !prevState);
   };

   const { setAccessToken, userRole, userID } = useContext(GlobalContext);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      getAllBuckets(accessToken, setAllFolders, setErrorMsg);
   }, [accessToken]);

   const refreshBuckets = async () => {
      await getAllBuckets(accessToken, setAllFolders, setErrorMsg);
   };

   const allData = allFolders.map((elem, index) => (
      <Folder elem={elem} key={index} refreshBuckets={refreshBuckets} setErrorMsg={setErrorMsg} setSuccessMsg={setSuccessMsg} />
   ));

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
         <div className="container">
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} />
            <SetSuccessMsg successMsg={successMsg} setSuccessMsg={setSuccessMsg} />
            <div className="py-2 my-3 rounded">
               <AddFolderModal refreshBuckets={refreshBuckets} />
            </div>
            <div>
               <div className="container">
                  <div className="row">{allData}</div>
               </div>
            </div>
         </div>
         <Footer />
      </div>
   );
};

export default HomePage;
