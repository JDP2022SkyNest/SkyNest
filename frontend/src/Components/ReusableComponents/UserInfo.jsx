import React, { useState } from "react";
import { useEffect } from "react";
import Footer from "../Footer/Footer";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import { getPersonalData } from "./ReusableFunctions";
import "./UserInfo.css";

const UserInfo = ({ userID }) => {
   const [userData, setUserData] = useState();
   const [errorMsg, setErrorMsg] = useState("");
   const accessToken = localStorage.accessToken;

   const role = userData?.roleName.slice(5).toUpperCase();

   useEffect(() => {
      if (userID) {
         getPersonalData(userID, accessToken, setUserData, setErrorMsg);
      }
      // eslint-disable-next-line
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
                           className="rounded-circle img-fluid avatar-icon-width"
                        />
                        <h5 className="my-3">
                           {userData?.name} {userData?.surname}
                        </h5>
                        <p className="text-muted mb-1">Full Stack Developer</p>
                        <p className={`text-${role === "ADMIN" ? "danger" : "mutted"} mb-4`}>{role}</p>
                        <div className="d-flex justify-content-center mb-2">
                           <button type="button" className={`btn btn-${role === "ADMIN" ? "danger" : "primary"}`}>
                              Follow
                           </button>
                           <button type="button" className={`btn btn-outline-${role === "ADMIN" ? "danger" : "primary"} ms-1`}>
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
                              <i className="fab fa-github fa-lg github-icon-color" />
                              <p className="mb-0">Placeholder</p>
                           </li>
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fab fa-twitter fa-lg twitter-icon-color" />
                              <p className="mb-0">Placeholder</p>
                           </li>
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fab fa-instagram fa-lg instagram-icon-color" />
                              <p className="mb-0">Placeholder</p>
                           </li>
                           <li className="list-group-item d-flex justify-content-between align-items-center p-3">
                              <i className="fab fa-facebook-f fa-lg facebook-icon-color" />
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
                           <div className="col-sm-3">Name:</div>
                           <div className="col-sm-9 text-mutted">{userData?.name}</div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">Last Name:</div>
                           <div className="col-sm-9 text-mutted">{userData?.surname}</div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">Email:</div>
                           <div className="col-sm-9 text-mutted">{userData?.email}</div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">Phone Number:</div>
                           <div className="col-sm-9 text-mutted">{userData?.phoneNumber}</div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">Address:</div>
                           <div className="col-sm-9 text-mutted">{userData?.address}</div>
                        </div>
                        <hr />
                        <div className="row">
                           <div className="col-sm-3">ID:</div>
                           <div className="col-sm-9 text-mutted">{userData?.id}</div>
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
