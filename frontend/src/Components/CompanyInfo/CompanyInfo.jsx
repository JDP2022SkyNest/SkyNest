import React, { useState, useEffect, useContext } from "react";
import Footer from "../Footer/Footer";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import LoaderAnimation from "../Loader/LoaderAnimation";
import { getCompany, editCompany } from "../ReusableComponents/ReusableFunctions";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";
import "./CompanyInfo.css";
import CompanyDetails from "./CompanyDetails";
import GlobalContext from "../context/GlobalContext";
import ROLE from "../Roles/Roles";

const CompanyInfo = () => {
   const [companyData, setCompanyData] = useState();
   const [clonedData, setClonedData] = useState();
   const [edit, setEdit] = useState(false);
   const [loading, setLoading] = useState(false);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const accessToken = localStorage.accessToken;

   const { userRole } = useContext(GlobalContext);

   const getCompanyData = async () => {
      setLoading(true);
      await getCompany(accessToken, setCompanyData, setErrorMsg);
      setLoading(false);
   };

   const onCompanyEdit = () => {
      editCompany(accessToken, clonedData, setErrorMsg, setSuccessMsg, onCompanyInfoChanged);
   };

   const onCompanyInfoChanged = async () => {
      await getCompany(accessToken, setCompanyData, setErrorMsg);
      setEdit(false);
   };

   useEffect(() => {
      getCompanyData();
      // eslint-disable-next-line
   }, []);

   useEffect(() => {
      const clone = Object.assign({}, companyData);
      delete clone.id;
      delete clone.pib;
      delete clone.tierName;
      delete clone.email;
      setClonedData(clone);
   }, [companyData]);

   return (
      <section className="company-page-body">
         <NavbarPanel name="Company Info" searchBar={false} path={ROUTES.HOME} />
         {loading ? (
            <div className="mt-5">
               <LoaderAnimation />
            </div>
         ) : (
            <div className="">
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} />
               <SetSuccessMsg successMsg={successMsg} setSuccessMsg={setSuccessMsg} />
               {!!companyData && (
                  <div className="col-12 col-sm-10 col-md-10 col-lg-6 offset-sm-1 offset-md-1 offset-lg-3 mb-5 py-3">
                     <div className="card mb-3 shadow">
                        <div className="card-body">
                           <CompanyDetails companyData={companyData} edit={edit} clonedData={clonedData} setClonedData={setClonedData} />
                        </div>
                     </div>
                     <div className={`${userRole !== ROLE.ADMIN && "d-none"}`}>
                        {edit ? (
                           <div className="d-flex flex-row-reverse">
                              <div className="mb-2 mr-1">
                                 <button onClick={onCompanyEdit} className="btn btn-secondary">
                                    Update
                                 </button>
                              </div>
                              <div className="mb-2 mr-1">
                                 <button onClick={() => setEdit(!edit)} className="btn btn-outline-secondary">
                                    Cancel
                                 </button>
                              </div>
                           </div>
                        ) : (
                           <div className="d-flex flex-row-reverse mb-2 mr-1">
                              <button onClick={() => setEdit(!edit)} className="btn btn-secondary">
                                 Edit
                              </button>
                           </div>
                        )}
                     </div>
                  </div>
               )}
            </div>
         )}
         <Footer />
      </section>
   );
};

export default CompanyInfo;
