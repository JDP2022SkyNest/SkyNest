import React from "react";
import { useState } from "react";
import { Accordion } from "react-bootstrap";

const AccordionUsers = ({ elem, index, deleteUser, accessToken, setChange, change, userID }) => {
   const [youSure, setYouSure] = useState(false);

   const userRoleName = elem.roleName.slice(5);

   return (
      <Accordion.Item eventKey={index}>
         <Accordion.Header>
            <div className="users-number-style">
               {elem.name} {elem.surname}
            </div>
            <span
               className={`ml-1 badge bg-${
                  userRoleName === "admin" ? "danger" : "secondary rounded-pill font-weight-normal "
               } py-1 users-badge-align`}
            >
               {userRoleName}
            </span>
            <span className="badge bg-primary ml-1 py-1 users-badge-align rounded-pill">{userID === elem.id && "You"}</span>
         </Accordion.Header>
         <Accordion.Body>
            <p>
               <span className="font-weight-bold">Email: </span>
               {elem.email}
            </p>
            <p>
               <span className="font-weight-bold">Phone Number: </span>
               {elem.phoneNumber}
            </p>
            <p>
               <span className="font-weight-bold">Address: </span>
               {elem.address}
            </p>
            <p>
               <span className="font-weight-bold">User ID: </span>
               {elem.id}
            </p>
            {userRoleName !== "admin" && (
               <div className="d-flex justify-content-between">
                  <div>
                     {/* This button is still a placeholder, functinality will be added */}
                     <button className="btn btn-info text-white">Promote</button>
                     <button className="btn btn-primary ml-2">Demote</button>
                  </div>
                  <div>
                     <button
                        onClick={async () => {
                           setYouSure(!youSure);
                           if (youSure) {
                              await deleteUser(accessToken, elem.id);
                              setChange(!change);
                           }
                        }}
                        className={`btn btn-${!youSure ? "danger" : "dark"}`}
                     >
                        {!youSure ? "Delete" : "You sure?"}
                     </button>
                  </div>
               </div>
            )}
         </Accordion.Body>
      </Accordion.Item>
   );
};

export default AccordionUsers;
