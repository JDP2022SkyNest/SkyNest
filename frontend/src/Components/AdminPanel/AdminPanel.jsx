import React, { useState, useEffect } from "react";
import { Accordion, Container } from "react-bootstrap";
import { getAllUsers, deleteUser } from "../ReusableComponents/ReusableFunctions";
import AdminPanelNav from "./AdminPanelNav";
import AdminCard from "./AdminCard";
import "./AdminPanel.css";
import AccordionUsers from "./AccordionUsers";

const AdminPanel = () => {
   const [usersData, setUsersData] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const accessToken = localStorage.accessToken;

   const timeNow = new Date().toLocaleDateString();

   useEffect(() => {
      getAllUsers(accessToken, setUsersData, setErrorMsg);
   }, [accessToken]);

   const allUsers = usersData.map((elem, index) => (
      <AccordionUsers elem={elem} index={index} deleteUser={deleteUser} key={elem.id} accessToken={accessToken} />
   ));

   return (
      <div>
         <AdminPanelNav />
         <Container>
            <p className={errorMsg ? "alert alert-danger text-danger text-center col-6 offset-3 mt-4" : "d-none"}>{errorMsg}</p>
            <div className="row">
               <div className="col-12 col-sm-6 col-lg-3 offset-lg-3">
                  <AdminCard title="Total Users:" body={usersData.length} color={"danger"} />
               </div>
               <div className="col-12 col-sm-6 col-lg-3 offset-lg-0">
                  <AdminCard title="More Info:" body={timeNow} color={"secondary"} />
               </div>
            </div>
            <div className="row">
               <div className="col-12 col-lg-8 offset-lg-2">
                  <Accordion className="shadow">{allUsers}</Accordion>
               </div>
            </div>
         </Container>
      </div>
   );
};

export default AdminPanel;
