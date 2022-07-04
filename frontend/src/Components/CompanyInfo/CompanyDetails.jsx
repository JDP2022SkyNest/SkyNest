import React from "react";

const CompanyDetails = ({ companyData, edit, clonedData, setClonedData }) => {
   const onNameChange = (e) => {
      setClonedData({ ...clonedData, name: e.target.value });
   };

   const onAddressChange = (e) => {
      setClonedData({ ...clonedData, address: e.target.value });
   };

   const onPhoneChange = (e) => {
      setClonedData({ ...clonedData, phoneNumber: e.target.value });
   };

   return (
      <section>
         <div className="row">
            <div className="col-sm-3 p-2">Name:</div>
            {!edit ? (
               <div className="col-sm-9 p-2 text-mutted">{companyData.name}</div>
            ) : (
               <div className="col-sm-9">
                  <input type="text" onChange={(e) => onNameChange(e)} value={clonedData.name} className="form-control border-info" />
               </div>
            )}
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2">Email:</div>
            <div className="col-sm-9 p-2 text-mutted">{companyData.email}</div>
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2">Address:</div>
            {!edit ? (
               <div className="col-sm-9 p-2 text-mutted">{companyData.name}</div>
            ) : (
               <div className="col-sm-9">
                  <input type="text" onChange={(e) => onAddressChange(e)} value={clonedData.address} className="form-control border-info" />
               </div>
            )}
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2">Phone Number:</div>

            {!edit ? (
               <div className="col-sm-9 p-2 text-mutted">{companyData.phoneNumber}</div>
            ) : (
               <div className="col-sm-9">
                  <input type="number" onChange={(e) => onPhoneChange(e)} value={clonedData.phoneNumber} className="form-control border-info" />
               </div>
            )}
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2">PIB:</div>
            <div className="col-sm-9 p-2 text-mutted">{companyData.pib}</div>
         </div>
         <hr />
         <div className="row">
            <div className="col-sm-3 p-2">Tier:</div>
            <div className="col-sm-9 p-2 text-mutted">{companyData.tierName}</div>
         </div>
      </section>
   );
};

export default CompanyDetails;
