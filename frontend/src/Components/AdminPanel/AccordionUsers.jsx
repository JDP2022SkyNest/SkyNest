import React from "react";
import { useState } from "react";
import { Accordion } from "react-bootstrap";
import * as MdIcons from "react-icons/md";
import * as AiIcons from "react-icons/ai";
import * as VscIcons from "react-icons/vsc";
import { disableUser, enableUser, promoteUser, demoteUser } from "../ReusableComponents/ReusableFunctions";

const AccordionUsers = ({ elem, index, setChange, change, userID, setErrorMsg, setSuccessMsg, setWarningMsg }) => {
   const [youSure, setYouSure] = useState(false);
   const userRoleName = elem.roleName.slice(5);
   const accessToken = localStorage.accessToken;

   return (
      <Accordion.Item eventKey={index} className={`${!elem.verified}`}>
         <Accordion.Header>
            {userRoleName !== "manager" ? (
               <span className={`ml-1 badge ${userRoleName === "admin" ? "admin-color" : "bg-secondary rounded-pill"} py-1 users-badge-align mr-3`}>
                  <AiIcons.AiOutlineUser className="main-icon-align" />
               </span>
            ) : (
               <span className="ml-1 badge manager-badge text-white rounded-pill mr-3">
                  <AiIcons.AiOutlineUser className="main-icon-align" />
               </span>
            )}
            <div
               className={`default-style ${
                  !elem.verified
                     ? "unverified-style text-muted"
                     : `${userRoleName === "manager" ? "manager-style" : `${userRoleName === "admin" ? "admin-style" : "worker-style"}`}`
               }`}
            >
               {elem.name} {elem.surname}
               <VscIcons.VscUnverified className={`${!elem.verified ? "ml-3" : "d-none"}`} />
            </div>
            <span className="badge border border-secondary text-dark ml-1 py-1 users-badge-align rounded-pill">{userID === elem.id && "You"}</span>

            {!elem.enabled && (
               <span className="badge text-dark ml-0 py-1">
                  <MdIcons.MdOutlinePersonAddDisabled />
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
                           className="btn alert-success text-success"
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
                           className="btn alert-warning text-dark"
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
                           className={`btn alert-${!youSure ? "danger" : "dark"}`}
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
