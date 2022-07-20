import React, { useState, useEffect, useContext } from "react";
import Footer from "../Footer/Footer";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo, getAllBuckets, sideBarCloseOnPhone } from "../ReusableComponents/ReusableFunctions";
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
import LoaderAnimation from "../Loader/LoaderAnimation";
import HomeSearchBar from "./HomeSearchBar";

const HomePage = () => {
   const navigate = useNavigate();
   const [sidebar, setSidebar] = useState(true);
   const [allBuckets, setAllBuckets] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const [searchTerm, setSearchTerm] = useState("");
   const [delState, setDelState] = useState(false);
   const filteredBuckets = allBuckets.filter((el) => !!el.deletedOn === delState && el.name.includes(searchTerm));
   // eslint-disable-next-line
   const [isMobile, setIsMobile] = useState(window.innerWidth < 1200);
   const [successMsg, setSuccessMsg] = useState("");
   const [loader, setLoader] = useState(false);
   const toggleSidebar = () => {
      setSidebar((prevState) => !prevState);
   };

   const { setAccessToken, userRole, userID } = useContext(GlobalContext);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      sideBarCloseOnPhone(isMobile, setSidebar);
   }, [isMobile]);

   useEffect(() => {
      let getBuckets = async () => {
         setLoader(true);
         await getAllBuckets(accessToken, setAllBuckets, setErrorMsg);
         setLoader(false);
      };
      getBuckets();
   }, [accessToken]);

   const refreshBuckets = async () => {
      setLoader(true);
      await getAllBuckets(accessToken, setAllBuckets, setErrorMsg);
      setLoader(false);
   };

   const allData = filteredBuckets.map((elem, index) => (
      <Bucket elem={elem} key={index} refreshBuckets={refreshBuckets} setErrorMsg={setErrorMsg} setSuccessMsg={setSuccessMsg} />
   ));

   return (
      <div className="home-page-body">
         <BackDrop sidebar={!isMobile && sidebar} closeSidebar={toggleSidebar} />
         <Navbar className="header py-0 bg-dark text-white">
            <Container>
               <ToolBar openSidebar={toggleSidebar} />
               <HomeSearchBar
                  searchBar={true}
                  path={ROUTES.HOME}
                  searchTerm={searchTerm}
                  setSearchTerm={setSearchTerm}
                  homeSearch
                  setDelState={setDelState}
                  placeholder="Search..."
               />
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
            {!loader ? (
               <div>
                  <div className="py-2 mt-2 mb-1 rounded d-flex">
                     <AddBucketModal refreshBuckets={refreshBuckets} />
                  </div>
                  <div className="container">
                     <div className="row data-folder">{allData}</div>
                  </div>
               </div>
            ) : (
               <div className="mt-5">
                  <LoaderAnimation />
               </div>
            )}
         </div>
         <Footer />
      </div>
   );
};

export default HomePage;