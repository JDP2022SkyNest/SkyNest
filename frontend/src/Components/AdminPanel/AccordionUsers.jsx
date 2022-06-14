import React from "react";
import { Accordion } from "react-bootstrap";

const AccordionUsers = ({ elem, index, deleteUser, accessToken }) => {
   return (
      <Accordion.Item eventKey={index}>
         <Accordion.Header>
            <div className="users-number-style">{index + 1}</div> - {elem.name} {elem.surname}
         </Accordion.Header>
         <Accordion.Body>
            <p>Email: {elem.email}</p>
            <p>Phone Number: {elem.phoneNumber}</p>
            <p>Address: {elem.address}</p>
            <p>User ID: {elem.id}</p>
            <button
               onClick={() => {
                  deleteUser(accessToken, elem.id);
               }}
               className="btn btn-danger"
            >
               Delete
            </button>
            {/* This button is still a placeholder, functinality will be added */}
            <button className="btn btn-success ml-2">Promote</button>
            <button className="btn btn-warning ml-2">Demote</button>
         </Accordion.Body>
      </Accordion.Item>
   );
};

export default AccordionUsers;
