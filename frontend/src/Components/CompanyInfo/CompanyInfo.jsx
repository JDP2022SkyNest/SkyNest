import React from "react";
import { useState } from "react";
import Footer from "../Footer/Footer";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import "./CompanyInfo.css";

const CompanyInfo = () => {
   const [edit, setEdit] = useState(false);
   return (
      <div className="company-page-body">
         <NavbarPanel name="User Info" searchBar={false} path={ROUTES.HOME} />

         <div className="ro">
            <div className="col-12 col-sm-10 col-md-10 col-lg-6 offset-sm-1 offset-md-1 offset-lg-3 mb-5 py-3">
               <div className="card mb-3 shadow">
                  <div className="card-body">
                     <p>loli</p>
                  </div>
               </div>
               {edit ? (
                  <div className="d-flex flex-row-reverse mb-2 mr-1">
                     <button className="btn btn-secondary">Update</button>
                  </div>
               ) : (
                  <div className="d-flex flex-row-reverse mb-2 mr-1">
                     <button onClick={() => setEdit(!edit)} className="btn btn-outline-secondary">
                        Edit
                     </button>
                  </div>
               )}
            </div>
            <Footer />
         </div>
      </div>
   );
};

export default CompanyInfo;
