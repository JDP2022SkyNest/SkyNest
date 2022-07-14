import React, { useState, useEffect, useContext } from "react";
import Footer from "../Footer/Footer";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo, getAllBuckets, sideBarCloseOnPhone, getRefreshToken } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import ROLE from "../Roles/Roles";
import { useNavigate } from "react-router-dom";
import Profile from "../HomePage/Profile/Profile";
import ToolBar from "../HomePage/ToolBar/ToolBar";
import SideBar from "../HomePage/SideBar/SideBar";
import BackDrop from "../HomePage/BackDrop/BackDrop";
import * as RiCions from "react-icons/ri";
import GlobalContext from "../context/GlobalContext";
import AddBucketModal from "./Bucket/AddBucketModal";
import Bucket from "./Bucket/Bucket";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";
import "./HomePage.css";

const HomePage = () => {
   const navigate = useNavigate();
   const [sidebar, setSidebar] = useState(true);
   const [allFolders, setAllFolders] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   // eslint-disable-next-line
   const [isMobile, setIsMobile] = useState(window.innerWidth < 1200);
   const [successMsg, setSuccessMsg] = useState("");
   const [searchQuery, setSearchQuery] = useState("");

   const toggleSidebar = () => {
      setSidebar((prevState) => !prevState);
   };

   const { setAccessToken, userRole, userID } = useContext(GlobalContext);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      sideBarCloseOnPhone(isMobile, setSidebar);
   }, [isMobile]);

   useEffect(() => {
      getAllBuckets(accessToken, setAllFolders, setErrorMsg);
   }, [accessToken]);

   const refreshBuckets = async () => {
      await getAllBuckets(accessToken, setAllFolders, setErrorMsg);
   };

   getRefreshToken();

   const allData = allFolders
      .filter((elem) => elem.name.toLowerCase().includes(searchQuery) || elem.description.toLowerCase().includes(searchQuery))
      .map((elem, index) => (
         <Bucket elem={elem} key={index} refreshBuckets={refreshBuckets} setErrorMsg={setErrorMsg} setSuccessMsg={setSuccessMsg} />
      ));
   return (
      <div className="home-page-body">
         <BackDrop sidebar={!isMobile && sidebar} closeSidebar={toggleSidebar} />
         <Navbar className="header py-0 bg-dark text-white">
            <Container>
               <ToolBar openSidebar={toggleSidebar} />
               <div className="input-group" style={{ width: "200px" }}>
                  <input
                     type="text"
                     className="form-control"
                     aria-label="Text input with segmented dropdown button"
                     placeholder="Search Buckets"
                     onChange={(e) => setSearchQuery(e.target.value.toLowerCase())}
                  />
               </div>
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
         <SideBar sidebar={!isMobile && sidebar} userRole={userRole} />
         <div className="container">
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="alert alert-danger text-danger text-center col-12 mt-3" />
            <SetSuccessMsg
               successMsg={successMsg}
               setSuccessMsg={setSuccessMsg}
               customStyle="alert alert-success text-success text-center col-12 mt-3"
            />
            <div className="py-2 my-3 rounded">
               <AddBucketModal refreshBuckets={refreshBuckets} />
            </div>
            <div>
               <div className="container">
                  <div className="row data-folder">{allData}</div>
               </div>
            </div>
         </div>
         <Footer />
      </div>
   );
};

export default HomePage;
