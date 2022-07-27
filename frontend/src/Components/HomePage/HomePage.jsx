import React, { useState, useEffect, useContext } from "react";
import Footer from "../Footer/Footer";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo, getAllBuckets, startDropboxAutorization, finishDropboxAutorization } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import ROLE from "../Roles/Roles";
import { useNavigate } from "react-router-dom";
import Profile from "../HomePage/Profile/Profile";
import * as RiCions from "react-icons/ri";
import GlobalContext from "../context/GlobalContext";
import AddBucketModal from "./Bucket/AddBucketModal";
import Bucket from "./Bucket/Bucket";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";
import "./HomePage.css";
import LoaderAnimation from "../Loader/LoaderAnimation";
import HomeSearchBar from "./HomeSearchBar";
import CreateNewTag from "./Tags/CreateNewTag";
import LambdaIcon from "../HomePage/lambda.png";

const HomePage = () => {
   const navigate = useNavigate();
   const [allBuckets, setAllBuckets] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const [searchTerm, setSearchTerm] = useState("");
   const [delState, setDelState] = useState(false);
   const [code, setCode] = useState("");
   const filteredBuckets = allBuckets.filter(
      (el) => !!el.deletedOn === delState && (el.name.includes(searchTerm) || el.tags.find((x) => x.name.includes(searchTerm)))
   );
   // eslint-disable-next-line
   const [successMsg, setSuccessMsg] = useState("");
   const [loader, setLoader] = useState(false);

   const { setAccessToken, userRole, userID, setMoveFilderID, setMoveFileID } = useContext(GlobalContext);
   const accessToken = localStorage.accessToken;

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

   useEffect(() => {
      setMoveFilderID("");
      setMoveFileID("");
      //eslint-disable-next-line
   }, []);

   const handleLambda = async (e) => {
      e.preventDefault();
      await finishDropboxAutorization();
   };

   return (
      <div className="home-page-body">
         <Navbar bg="dark" variant="dark">
            <Container>
               <Navbar.Brand className="text-white d-none d-md-block m-0 fixed-navbar-width">SkyNest</Navbar.Brand>
               <HomeSearchBar
                  searchBar={true}
                  path={ROUTES.HOME}
                  searchTerm={searchTerm}
                  setSearchTerm={setSearchTerm}
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
         <div className="container">
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="alert alert-danger text-danger text-center col-12 mt-3" />
            <SetSuccessMsg
               successMsg={successMsg}
               setSuccessMsg={setSuccessMsg}
               customStyle="alert alert-success text-success text-center col-12 mt-3"
            />
            {loader && <LoaderAnimation />}
            <div>
               <div className="py-2 mt-2 rounded d-flex">
                  <AddBucketModal refreshBuckets={refreshBuckets} />
                  <CreateNewTag />
               </div>
               <div className="py-2 mt-2 rounded d-flex">
                  <div
                     onClick={() => {
                        startDropboxAutorization(accessToken, setErrorMsg);
                     }}
                     className="ml-2 latte-background custom-rounded shadow"
                  >
                     <img src={LambdaIcon} alt="lambda" className="lambda-icon" />
                  </div>
                  <form onSubmit={handleLambda}>
                     <div>
                        <input className="ml-2 border border-white" value={code} onChange={(e) => setCode(e.target.value)} placeholder="Enter code" />
                     </div>
                     <button className="ml-2 btn btn-light" type="submit">
                        Connect to DROPBOX
                     </button>
                  </form>
               </div>
               <div className="container mt-2">
                  <div className="row data-folder">{allData}</div>
               </div>
            </div>
         </div>

         <Footer />
      </div>
   );
};

export default HomePage;
