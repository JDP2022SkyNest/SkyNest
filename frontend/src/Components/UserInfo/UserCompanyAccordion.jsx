import React from "react";
import { Accordion } from "react-bootstrap";

const UserCompanyAccordion = ({ companyData }) => {
   return (
      <Accordion>
         <Accordion.Item eventKey="0">
            <Accordion.Header>
               <i className="fas fa-globe fa-lg text-dark"></i>
               <div className="ml-3">Company Details</div>
            </Accordion.Header>
            <Accordion.Body>
               <div className="row">
                  <strong className="col-sm-4">Name:</strong>
                  <div className="col-sm-8 text-mutted">{companyData.name}</div>
               </div>
               <hr />
               <div className="row">
                  <strong className="col-sm-4">Email:</strong>
                  <div className="col-sm-8 text-mutted">{companyData.email}</div>
               </div>
               <hr />
               <div className="row">
                  <strong className="col-sm-4">Address:</strong>
                  <div className="col-sm-8 text-mutted">{companyData.address}</div>
               </div>
               <hr />
               <div className="row">
                  <strong className="col-sm-4">Phone Number:</strong>
                  <div className="col-sm-8 text-mutted">{companyData.phoneNumber}</div>
               </div>
               <hr />
               <div className="row">
                  <strong className="col-sm-4">Pib</strong>
                  <div className="col-sm-8 text-mutted">{companyData.pib}</div>
               </div>
               <hr />
               <div className="row">
                  <strong className="col-sm-4">Tier</strong>
                  <div className="col-sm-8 text-mutted">{companyData.tierName}</div>
               </div>
            </Accordion.Body>
         </Accordion.Item>
      </Accordion>
   );
};

export default UserCompanyAccordion;
