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
      <section className="container">
         <div className="row">
            <strong className="col-sm-4 p-2">Name:</strong>
            {!edit ? (
               <div className="col-sm-8 p-2 text-mutted">{companyData.name}</div>
            ) : (
               <div className="col-sm-8">
                  <input type="text" onChange={(e) => onNameChange(e)} value={clonedData.name} className="form-control border-white" />
               </div>
            )}
         </div>
         <hr className="my-1 my-sm-3" />
         <div className="row">
            {!edit && (
               <>
                  <strong className="col-sm-4 p-2">Email:</strong>
                  <div className="col-sm-8 p-2  text-mutted">{companyData.email}</div>
               </>
            )}
         </div>
         {!edit && <hr className="my-1 my-sm-3" />}
         <div className="row">
            <strong className="col-sm-4 p-2">Address:</strong>
            {!edit ? (
               <div className="col-sm-8 p-2 text-mutted">{companyData.address}</div>
            ) : (
               <div className="col-sm-8">
                  <input type="text" onChange={(e) => onAddressChange(e)} value={clonedData.address} className="form-control border-white" />
               </div>
            )}
         </div>
         <hr className="my-1 my-sm-3" />
         <div className="row">
            <strong className="col-sm-4 p-2">Phone Number:</strong>

            {!edit ? (
               <div className="col-sm-8 p-2 text-mutted">{companyData.phoneNumber}</div>
            ) : (
               <div className="col-sm-8">
                  <input type="number" onChange={(e) => onPhoneChange(e)} value={clonedData.phoneNumber} className="form-control border-white" />
               </div>
            )}
         </div>
         {!edit && <hr className="my-1 my-sm-3" />}
         <div className="row">
            {!edit && (
               <>
                  <strong className="col-sm-4 p-2">PIB:</strong>
                  <div className="col-sm-8 p-2 text-mutted">{companyData.pib}</div>
               </>
            )}
         </div>
         {!edit && <hr className="my-1 my-sm-3" />}
         <div className="row">
            {!edit && (
               <>
                  <strong className="col-sm-4 p-2">Tier:</strong>
                  <div className="col-sm-8 p-2 text-mutted">{companyData.tierName}</div>
               </>
            )}
         </div>
      </section>
   );
};

export default CompanyDetails;
