import React, { useState } from "react";
import { GrantFolderPermission } from "../../../ReusableComponents/ReusableFunctions";
import ModalLoader from "../../../Loader/ModalLoader";

const FolderGrantPermission = ({ objectId, setErrorMsg, errorMsg, setSuccessMsg }) => {
   const [email, setEmail] = useState("");
   const [role, setRole] = useState("view");
   const [loading, setLoading] = useState(false);
   const accessToken = localStorage.accessToken;

   const onSubmit = async () => {
      if (email.length > 5) {
         setLoading(true);
         await GrantFolderPermission(accessToken, email, objectId, role, setErrorMsg, setSuccessMsg);
         setLoading(false);
      } else if (!email.includes("@")) {
         setErrorMsg("Invalid email format");
      } else {
         setErrorMsg("Email has to be more than 5 characters");
      }
   };

   return (
      <div className="px-3">
         <fieldset disabled={loading}>
            <div className="form-outline">
               <label className="form-label" htmlFor="emailInput">
                  Email address
               </label>
               <input
                  type="email"
                  onChange={(e) => setEmail(e.target.value)}
                  value={email}
                  id="emailInput"
                  className={`form-control form-control ${errorMsg ? "border-danger" : null}`}
                  required
                  autoComplete="off"
               />
               <div className="mt-3">
                  <button onClick={() => setRole("view")} className={`btn btn-${role === "view" ? "secondary" : "outline-secondary"}`}>
                     View
                  </button>
                  <button onClick={() => setRole("download")} className={`btn ml-2 btn-${role === "download" ? "secondary" : "outline-secondary"}`}>
                     Download
                  </button>
                  <button onClick={() => setRole("edit")} className={`btn ml-2 btn-${role === "edit" ? "secondary" : "outline-secondary"}`}>
                     Edit
                  </button>
                  <button onClick={() => setRole("owner")} className={`btn ml-2 btn-${role === "owner" ? "secondary" : "outline-secondary"}`}>
                     Owner
                  </button>
               </div>
               <div className="mt-3">
                  {!loading ? (
                     <button onClick={onSubmit} className="btn btn-secondary button-width" disabled={!email}>
                        Grant
                     </button>
                  ) : (
                     <ModalLoader />
                  )}
               </div>
            </div>
         </fieldset>
      </div>
   );
};

export default FolderGrantPermission;
