import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import { getPersonalData, editUserData, onUserLogout, getCompany } from "../ReusableComponents/ReusableFunctions";
import UserCardDetails from "./UserCardDetails";
import "./UserInfo.css";
import LoaderAnimation from "../Loader/LoaderAnimation";
import UserCompanyAccordion from "./UserCompanyAccordion";
import SetErrorMsg from "../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../ReusableComponents/SetSuccessMsg";
import { useContext } from "react";
import GlobalContext from "../context/GlobalContext";

const UserInfo = () => {
   const [userData, setUserData] = useState();
   const [companyData, setCompanyData] = useState();
   const [clonedData, setClonedData] = useState();
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);
   const [edit, setEdit] = useState(false);

   const { userID, accessToken, setAccessToken } = useContext(GlobalContext);

   const role = userData?.roleName.slice(5).toUpperCase();

   const nameChange = (e) => {
      setUserData({ ...userData, name: e.target.value });
   };

   const surnameChange = (e) => {
      setUserData({ ...userData, surname: e.target.value });
   };

   const phoneChange = (e) => {
      setUserData({ ...userData, phoneNumber: e.target.value });
   };

   const addressChange = (e) => {
      setUserData({ ...userData, address: e.target.value });
   };

   const positionChange = (e) => {
      setUserData({ ...userData, positionInCompany: e.target.value });
   };

   const onUserInfoChanged = async () => {
      await editUserData(accessToken, userData?.id, clonedData, setSuccessMsg, setErrorMsg, refreshTheData);
      setEdit(false);
   };

   const refreshTheData = async () => {
      await getPersonalData(userID, accessToken, setUserData, setErrorMsg);
   };

   useEffect(() => {
      const onDataLoading = async () => {
         if (userID) {
            setLoading(true);
            await getPersonalData(userID, accessToken, setUserData, setErrorMsg);
            await getCompany(accessToken, setCompanyData, setErrorMsg, false);
            setLoading(false);
         }
      };
      onDataLoading();
      // eslint-disable-next-line
   }, [userID]);

   useEffect(() => {
      const clone = Object.assign({}, userData);
      delete clone.email;
      delete clone.id;
      delete clone.roleName;
      setClonedData(clone);
   }, [userData]);

   return (
      <section className="user-page-body">
         <NavbarPanel name="User Info" searchBar={false} path={ROUTES.HOME} />
         {loading ? (
            <div className="mt-5">
               <LoaderAnimation />
            </div>
         ) : (
            <div className="container py-3">
               <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="alert alert-danger text-danger text-center" />
               <SetSuccessMsg successMsg={successMsg} setSuccessMsg={setSuccessMsg} customStyle="alert alert-success text-success text-center" />
               {userData && (
                  <div className="row">
                     <div className="col-lg-4">
                        <div className="card mb-4">
                           <div className="card-body text-center shadow">
                              <div className="rounded-circle img-fluid avatar-icon-width border border-mutted">
                                 <div className="text-dark">
                                    {userData.name[0]}
                                    {userData.surname[0]}
                                 </div>
                              </div>
                              <h5 className="my-3">
                                 {userData?.name} {userData?.surname}
                              </h5>
                              <p className="text-muted mb-1">
                                 {userData?.positionInCompany === null ? "Position not set" : userData.positionInCompany}
                              </p>
                              <p className="admin-dark">{role}</p>
                              <div className="d-flex justify-content-center mb-2">
                                 <button
                                    type="button"
                                    onClick={() => {
                                       setEdit(!edit);
                                    }}
                                    className="btn btn-secondary button-width"
                                 >
                                    {edit ? "Cancel" : "Edit"}
                                 </button>
                                 <button
                                    onClick={() => {
                                       onUserLogout(accessToken, setAccessToken);
                                    }}
                                    type="button"
                                    className="btn btn-outline-secondary button-width ml-2"
                                 >
                                    Logout
                                 </button>
                              </div>
                           </div>
                        </div>
                        <div className="card mb-4 mb-lg-0 shadow">
                           <div className="card-body p-0">
                              {companyData ? (
                                 <ul className="list-group list-group-flush rounded-3">
                                    <UserCompanyAccordion companyData={companyData} />
                                 </ul>
                              ) : (
                                 <div className="m-3 text-center text-muted">User not part of any company</div>
                              )}
                           </div>
                        </div>
                     </div>
                     <div className="col-lg-8 mb-3">
                        <div className="card mb-3 shadow">
                           <div className="card-body">
                              <div className="container">
                                 <UserCardDetails
                                    info={<strong>Name:</strong>}
                                    result={userData?.name}
                                    edit={edit}
                                    func={nameChange}
                                 ></UserCardDetails>
                                 <UserCardDetails info={<strong>Last Name:</strong>} result={userData?.surname} edit={edit} func={surnameChange} />
                                 <UserCardDetails info={<strong>Email:</strong>} result={userData?.email} edit={edit} />
                                 <UserCardDetails
                                    info={<strong>Phone Number:</strong>}
                                    type="number"
                                    result={userData?.phoneNumber}
                                    edit={edit}
                                    func={phoneChange}
                                 />
                                 {edit && (
                                    <UserCardDetails
                                       info={<strong>Position:</strong>}
                                       result={userData?.positionInCompany}
                                       edit={edit}
                                       func={positionChange}
                                       placeholder="Please enter a value here"
                                    />
                                 )}
                                 <UserCardDetails
                                    info={<strong>Address:</strong>}
                                    result={userData?.address}
                                    edit={edit}
                                    func={addressChange}
                                    horLine={false}
                                 />
                              </div>
                           </div>
                        </div>
                        {edit && (
                           <div className="d-flex flex-row-reverse mb-2 mr-1">
                              <button onClick={onUserInfoChanged} className="btn btn-secondary button-width">
                                 Update
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

export default UserInfo;
