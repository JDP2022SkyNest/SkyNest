import React, { useState } from "react";
import { useEffect } from "react";
import Footer from "../Footer/Footer";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import { getPersonalData } from "./ReusableFunctions";

const UserInfo = ({ userID }) => {
   const [userData, setUserData] = useState();
   const [errorMsg, setErrorMsg] = useState("");
   const accessToken = localStorage.accessToken;

   const role = userData?.roleName.slice(5).toUpperCase();

   useEffect(() => {
      if (userID) {
         getPersonalData(userID, accessToken, setUserData, setErrorMsg);
      }
   }, [userID]);

   return (
      <section>
         <NavbarPanel name="User Info" searchBar={false} path={ROUTES.HOME} />
         <div className="container py-5">
            <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
            <div className="row">
               <div className="col-lg-4">
                  <div className="card mb-4">
                     <div className="card-body text-center shadow">
                        <img
                           src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp"
                           alt="avatar"
                           className="rounded-circle img-fluid"
                           style={{ width: "150px" }}
                        />
                        <h5 className="my-3">
                           {userData?.name} {userData?.surname}
                        </h5>
                        <p className="text-muted mb-1">Full Stack Developer</p>
                        <p className={`text-${role === "ADMIN" ? "danger" : "mutted"} mb-4`}>{role}</p>
                        <div className="d-flex justify-content-center mb-2">
                           <button type="button" className="btn btn-primary">
                              Follow
                           </button>
                           <button type="button" className="btn btn-outline-primary ms-1">
                              Message
                           </button>
                        </div>
                     </div>
                  </div>
                  <div className="card mb-4 mb-lg-0 shadow">
                     <div className="card-body p-0">
                        <ul className="list-group list-group-flush rounded-3">
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fas fa-globe fa-lg text-warning" />
                              <p className="mb-0">Placeholder</p>
                           </li>
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fab fa-github fa-lg" style={{ color: "#333333" }} />
                              <p className="mb-0">Placeholder</p>
                           </li>
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fab fa-twitter fa-lg" style={{ color: "#55acee" }} />
                              <p className="mb-0">Placeholder</p>
                           </li>
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fab fa-instagram fa-lg" style={{ color: "#ac2bac" }} />
                              <p className="mb-0">Placeholder</p>
                           </li>
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fab fa-facebook-f fa-lg" style={{ color: "#3b5998" }} />
                              <p className="mb-0">Placeholder</p>
                           </li>
                        </ul>
                     </div>
                  </div>
               </div>
               <div className="col-lg-8">
                  <div className="card mb-4 shadow">
                     <div className="card-body">
                        <div className="row">
                           <div className="col-sm-3">
                              <p className="mb-0">Name</p>
                           </div>
                           <div className="col-sm-9">
                              <p className="text-muted mb-0">{userData?.name}</p>
                           </div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">
                              <p className="mb-0">Last Name</p>
                           </div>
                           <div className="col-sm-9">
                              <p className="text-muted mb-0">{userData?.surname}</p>
                           </div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">
                              <p className="mb-0">Email</p>
                           </div>
                           <div className="col-sm-9">
                              <p className="text-muted mb-0">{userData?.email}</p>
                           </div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">
                              <p className="mb-0">Phone</p>
                           </div>
                           <div className="col-sm-9">
                              <p className="text-muted mb-0">{userData?.phoneNumber}</p>
                           </div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">
                              <p className="mb-0">Address</p>
                           </div>
                           <div className="col-sm-9">
                              <p className="text-muted mb-0">{userData?.address}</p>
                           </div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">
                              <p className="mb-0">ID:</p>
                           </div>
                           <div className="col-sm-9">
                              <p className="text-muted mb-0">{userData?.id}</p>
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         </div>
         <Footer />
      </section>
   );
};

export default UserInfo;
