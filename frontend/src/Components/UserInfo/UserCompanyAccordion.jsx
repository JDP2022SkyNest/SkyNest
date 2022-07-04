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
                  <div className="col-sm-3">Name:</div>
                  <div className="col-sm-9 text-mutted">{companyData.name}</div>
               </div>
               <hr />
               <div className="row">
                  <div className="col-sm-3">Email:</div>
                  <div className="col-sm-9 text-mutted">{companyData.email}</div>
               </div>
               <hr />
               <div className="row">
                  <div className="col-sm-3">Address:</div>
                  <div className="col-sm-9 text-mutted">{companyData.address}</div>
               </div>
               <hr />
               <div className="row">
                  <div className="col-sm-3">Phone Number:</div>
                  <div className="col-sm-9 text-mutted">{companyData.phoneNumber}</div>
               </div>
               <hr />
               <div className="row">
                  <div className="col-sm-3">Pib</div>
                  <div className="col-sm-9 text-mutted">{companyData.pib}</div>
               </div>
               <hr />
               <div className="row">
                  <div className="col-sm-3">Tier</div>
                  <div className="col-sm-9 text-mutted">{companyData.tierName}</div>
               </div>   
            </Accordion.Body>
         </Accordion.Item>
      </Accordion>
   );
};

export default UserCompanyAccordion;
