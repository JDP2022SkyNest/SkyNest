import React, { useState } from "react";
import { useEffect } from "react";
import Footer from "../Footer/Footer";
import NavbarPanel from "../ReusableComponents/NavbarPanel";
import ROUTES from "../Routes/ROUTES";
import SOCIALS from "./UserSocialInfo";
import { getPersonalData } from "../ReusableComponents/ReusableFunctions";
import UserCardDetails from "./UserCardDetails";
import LoaderAnimation from "../Loader/LoaderAnimation";
import "./UserInfo.css";

const UserInfo = ({ userID, accessToken }) => {
   const [userData, setUserData] = useState();
   const [errorMsg, setErrorMsg] = useState("");
   const [loading, setLoading] = useState(false);

   const role = userData?.roleName.slice(5).toUpperCase();

   useEffect(() => {
      (async function loading() {
         if (userID) {
            setLoading(true);
            await getPersonalData(userID, accessToken, setUserData, setErrorMsg);
            setLoading(true);
         }
      })();
      // eslint-disable-next-line
   }, [userID]);

   return (
      <section>
         <NavbarPanel name="User Info" searchBar={false} path={ROUTES.HOME} />
         {loading ? (
            <div className="container py-5">
               <p className={errorMsg ? "alert alert-danger text-danger text-center" : "d-none"}>{errorMsg}</p>
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
                           <p className="text-muted mb-1">Full Stack Developer</p>
                           <p className={`text-${role === "ADMIN" ? "danger" : "mutted"} mb-4`}>{role}</p>
                           <div className="d-flex justify-content-center mb-2">
                              <button type="button" className={`btn btn-${role === "ADMIN" ? "danger" : "primary"}`}>
                                 Follow
                              </button>
                              <button type="button" className={`btn btn-outline-${role === "ADMIN" ? "danger" : "primary"} ms-1`}>
                                 Message
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
                  <div className="col-lg-8">
                     <div className="card mb-4 shadow">
                        <div className="card-body">
                           <UserCardDetails info="Name:" result={userData?.name} />
                           <UserCardDetails info="Last Name:" result={userData?.surname} />
                           <UserCardDetails info="Email:" result={userData?.email} />
                           <UserCardDetails info="Phone Number:" result={userData?.phoneNumber} />
                           <UserCardDetails info="Address:" result={userData?.address} />
                           <UserCardDetails info="ID:" result={userData?.id} horLine={false} />
                        </div>
                     </div>
                  </div>
               </div>
            </div>
         ) : (
            <div className="mt-5">
               <LoaderAnimation />
            </div>
         )}

         <Footer />
      </section>
   );
};

export default UserInfo;
