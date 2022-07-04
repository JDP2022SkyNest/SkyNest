import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import SOCIALS from "./UserSocialInfo";
import { getPersonalData, editUserData, onUserLogout } from "../ReusableComponents/ReusableFunctions";
import UserCardDetails from "./UserCardDetails";
import "./UserInfo.css";
import LoaderAnimation from "../Loader/LoaderAnimation";

const UserInfo = ({ userID, accessToken, setAccessToken }) => {
   const [userData, setUserData] = useState();
   const [clonedData, setClonedData] = useState();
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [loading, setLoading] = useState(false);
   const [edit, setEdit] = useState(false);

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
               <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
               <p className={successMsg ? "alert alert-success text-success text-center" : "d-none"}>{successMsg}</p>
               {userData && (
                  <div className="row">
                     <div className="col-lg-4">
                        <div className="card mb-4">
                           <div className="card-body text-center shadow">
                              <img
                                 src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-chat/ava3.webp"
                                 alt="avatar"
                                 className="rounded-circle img-fluid avatar-icon-width"
                              />
                              <h5 className="my-3">
                                 {userData?.name} {userData?.surname}
                              </h5>
                              <p className="text-muted mb-1">
                                 {userData?.positionInCompany === null ? "Position: Undefined" : userData.positionInCompany}
                              </p>
                              <p className={`${role === "ADMIN" ? "admin-text" : `${role === "WORKER" ? "text-secondary" : "manager-text"}`}`}>
                                 {role}
                              </p>
                              <div className="d-flex justify-content-center mb-2">
                                 <button
                                    type="button"
                                    onClick={() => {
                                       setEdit(!edit);
                                    }}
                                    className={`btn ${
                                       role === "ADMIN" ? "admin-button" : `${role === "WORKER" ? "btn-secondary" : "manager-button"}`
                                    }`}
                                 >
                                    {edit ? "Cancel" : "Edit"}
                                 </button>
                                 <button
                                    onClick={() => {
                                       onUserLogout(accessToken, setAccessToken);
                                    }}
                                    type="button"
                                    className={`btn ${
                                       role === "ADMIN"
                                          ? "admin-edit-button"
                                          : `${role === "WORKER" ? "btn-outline-secondary ml-1" : "manager-edit-button"}`
                                    }`}
                                 >
                                    Logout
                                 </button>
                              </div>
                           </div>
                        </div>
                        <div className="card mb-4 mb-lg-0 shadow">
                           <div className="card-body p-0">
                              <ul className="list-group list-group-flush rounded-3">
                                 {SOCIALS.map((item, index) => {
                                    return (
                                       <li key={index} className="list-group-item d-flex justify-content-between align-items-center p-3">
                                          <i className={item.classId} />
                                          <p className="mb-0">{item.title}</p>
                                       </li>
                                    );
                                 })}
                              </ul>
                           </div>
                        </div>
                     </div>
                     <div className="col-lg-8 mb-5">
                        <div className="card mb-3 shadow">
                           <div className="card-body">
                              <UserCardDetails info="Name:" result={userData?.name} edit={edit} func={nameChange} />
                              <UserCardDetails info="Last Name:" result={userData?.surname} edit={edit} func={surnameChange} />
                              <UserCardDetails info="Email:" result={userData?.email} />
                              <UserCardDetails info="Phone Number:" type="number" result={userData?.phoneNumber} edit={edit} func={phoneChange} />
                              <UserCardDetails info="Address:" result={userData?.address} edit={edit} func={addressChange} horLine={false} />
                           </div>
                        </div>
                        {edit && (
                           <div className="d-flex flex-row-reverse mb-2 mr-1">
                              <button onClick={onUserInfoChanged} className="btn btn-secondary">
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
