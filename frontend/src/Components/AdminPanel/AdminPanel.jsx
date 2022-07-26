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
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";
import SetWarningMsg from "../ReusableComponents/SetWarningMsg";

const AdminPanel = () => {
   const [usersData, setUsersData] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [warningMsg, setWarningMsg] = useState("");
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
         user.roleName.includes(searchTerm) ||
         ((user.verified === searchTerm || user.enabled === searchTerm) && user.enabled === searchTerm && user.verified === searchTerm)
   );

   const allUsers = filterUsers.map((elem, index) => (
      <AccordionUsers
         elem={elem}
         index={index}
         key={elem.id}
         setChange={setChange}
         change={change}
         setErrorMsg={setErrorMsg}
         setSuccessMsg={setSuccessMsg}
         setWarningMsg={setWarningMsg}
      />
   ));

   return (
      <div className="admin-page-body">
         <NavbarPanel name={"Admin Panel"} searchBar={true} path={ROUTES.HOME} searchTerm={searchTerm} setSearchTerm={setSearchTerm}>
            <ReusableModal title="Instructions" buttonText="Close">
               <AdminCarousel />
            </ReusableModal>
         </NavbarPanel>
         {loading && <LoaderAnimation />}
         <Container>
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} />
            <SetSuccessMsg successMsg={successMsg} setSuccessMsg={setSuccessMsg} />
            <SetWarningMsg warningMsg={warningMsg} setWarningMsg={setWarningMsg} />
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
         </Container>
         <Footer />
      </div>
   );
};

export default AdminPanel;
