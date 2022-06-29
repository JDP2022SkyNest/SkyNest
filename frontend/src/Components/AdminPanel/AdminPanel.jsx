import React, { useState, useEffect } from "react";
import { Accordion, Container } from "react-bootstrap";
import { getAllUsers } from "../ReusableComponents/ReusableFunctions";
import AdminCard from "./AdminCard";
import "./AdminPanel.css";
import AccordionUsers from "./AccordionUsers";
import Footer from "../Footer/Footer";
import LoaderAnimation from "../Loader/LoaderAnimation";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ReusableModal from "../ReusableComponents/ReusableModal";
import AdminCarousel from "./AdminCarousel";
import ROUTES from "../Routes/ROUTES";

const AdminPanel = ({ userID }) => {
   const [usersData, setUsersData] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const [searchTerm, setSearchTerm] = useState("");
   const [change, setChange] = useState(false);
   const [loading, setLoading] = useState(false);
   const accessToken = localStorage.accessToken;

   useEffect(() => {
      (async function loading() {
         setLoading(true);
         await getAllUsers(accessToken, setUsersData, setErrorMsg);
         setLoading(false);
      })();
   }, [accessToken, change]);

   const filterUsers = usersData.filter(
      (user) =>
         user.name.includes(searchTerm) ||
         user.surname.includes(searchTerm) ||
         user.email.includes(searchTerm) ||
         user.address.includes(searchTerm) ||
         user.phoneNumber.includes(searchTerm) ||
         user.roleName.includes(searchTerm)
   );

   const allUsers = filterUsers.map((elem, index) => (
      <AccordionUsers elem={elem} index={index} key={elem.id} setChange={setChange} change={change} userID={userID} setErrorMsg={setErrorMsg} />
   ));

   return (
      <div className="admin-page-body">
         <NavbarPanel name={"Admin Panel"} searchBar={true} path={ROUTES.HOME} setSearchTerm={setSearchTerm}>
            <ReusableModal title="Instructions">
               <AdminCarousel />
            </ReusableModal>
         </NavbarPanel>
         {!loading ? (
            <Container>
               <div className="row">
                  <div className="col-6 col-md-4 col-lg-3 offset-lg-3 offset-md-2 offset-0 ">
                     <AdminCard title="Total Users:" body={usersData.length} color={"white"} />
                  </div>
                  <div className="col-6 col-md-4 col-lg-3 offset-lg-0">
                     <AdminCard title="Filtered Users:" body={filterUsers.length} color={"white"} />
                  </div>
               </div>
               <div className="row">
                  <div className="col-12 col-lg-8 offset-lg-2">
                     <Accordion className="shadow">{allUsers}</Accordion>
                  </div>
               </div>
               <p className={errorMsg ? "alert alert-danger text-danger text-center col-12 col-sm-6 offset-0 offset-sm-3 mt-4" : "d-none"}>
                  {errorMsg}
               </p>
            </Container>
         ) : (
            <div className="mt-5">
               <LoaderAnimation />
            </div>
         )}
         <Footer />
      </div>
   );
};

export default AdminPanel;
