import React from "react";
import { useState } from "react";
import { Accordion } from "react-bootstrap";
import * as TiIcons from "react-icons/ti";
import { disableUser, enableUser, promoteUser, demoteUser } from "../ReusableComponents/ReusableFunctions";

const AccordionUsers = ({ elem, index, setChange, change, userID, setErrorMsg, setSuccessMsg, setWarningMsg }) => {
   const [youSure, setYouSure] = useState(false);
   const userRoleName = elem.roleName.slice(5);
   const accessToken = localStorage.accessToken;

   return (
      <Accordion.Item eventKey={index} className={`${!elem.verified && "border border-danger"}`}>
         <Accordion.Header>
            <div className={`${!elem.verified ? "text-danger unverified-users-number-style " : "users-number-style"}`}>
               {elem.name} {elem.surname}
            </div>
            {userRoleName !== "manager" ? (
               <span className={`ml-1 badge ${userRoleName === "admin" ? "admin-color" : "bg-secondary rounded-pill"} py-1 users-badge-align`}>
                  {userRoleName}
               </span>
            ) : (
               <span className="ml-1 badge manager-badge rounded-pill">{userRoleName}</span>
            )}
            <span className="badge border border-secondary text-dark ml-1 py-1 users-badge-align rounded-pill">{userID === elem.id && "You"}</span>
            {!elem.enabled && (
               <span className="text-dark">
                  <TiIcons.TiDelete className="disabled-user-icon" />
               </span>
            )}
         </Accordion.Header>
         <Accordion.Body>
            {!elem.verified && <p className="text-danger">Unverified</p>}
            {!elem.enabled && <p className="text-muted">Disabled</p>}
            <p>
               <span className="font-weight-bold">Email: </span>
               {elem.email}
            </p>
            <p>
               <span className="font-weight-bold">Phone Number: </span>
               {elem.phoneNumber}
            </p>
            <p className="mb-0">
               <span className="font-weight-bold">Address: </span>
               {elem.address}
            </p>
            {userRoleName !== "admin" && (
               <div className="d-flex justify-content-between mt-3">
                  <div>
                     {userRoleName === "worker" && (
                        <button
                           onClick={async () => {
                              await promoteUser(accessToken, elem.id, setErrorMsg, setSuccessMsg);
                              setChange(!change);
                           }}
                           className="btn btn-success text-white"
                        >
                           Promote
                        </button>
                     )}
                     {userRoleName === "manager" && (
                        <button
                           onClick={async () => {
                              await demoteUser(accessToken, elem.id, setErrorMsg, setWarningMsg);
                              setChange(!change);
                           }}
                           className="btn btn-warning"
                        >
                           Demote
                        </button>
                     )}
                  </div>
                  <div>
                     {elem.enabled ? (
                        <button
                           onClick={async () => {
                              setYouSure(!youSure);
                              if (youSure) {
                                 await disableUser(accessToken, elem.id, setErrorMsg);
                                 setChange(!change);
                              }
                           }}
                           className={`btn btn-${!youSure ? "danger" : "dark"}`}
                        >
                           {!youSure ? "Disable" : "You sure?"}
                        </button>
                     ) : (
                        <button
                           onClick={async () => {
                              await enableUser(accessToken, elem.id, setErrorMsg);
                              setChange(!change);
                           }}
                           className="btn btn-outline-dark"
                        >
                           Enable
                        </button>
                     )}
                  </div>
               </div>
            )}
         </Accordion.Body>
      </Accordion.Item>
   );
};

export default AccordionUsers;
