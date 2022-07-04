import React, { useState, useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./Components/HomePage/HomePage";
import Login from "./Components/Login/Login";
import SignUp from "./Components/SignUp/SignUp";
import ProtectedRoute from "./Components/Routes/ProtectedRoute";
import RedirectRoute from "./Components/Routes/RedirectRoute";
import ForgotPassword from "./Components/ForgotPassword/ForgotPassword";
import ConfirmPassword from "./Components/ForgotPassword/ConfirmPassword";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import AdminPanel from "./Components/AdminPanel/AdminPanel";
import ResendEmail from "./Components/ResendEmail/ResendEmail";
import NoToken from "./Components/Routes/NoToken";
import AdminRoute from "./Components/Routes/AdminRoute";
import { getUserData } from "./Components/ReusableComponents/ReusableFunctions";
import UserInfo from "./Components/UserInfo/UserInfo";
import CompanyInfo from "./Components/CompanyInfo/CompanyInfo";

const App = () => {
   const [accessToken, setAccessToken] = useState(localStorage.accessToken);
   const [userRole, setUserRole] = useState("");
   const [userID, setUserID] = useState("");

   useEffect(() => {
      getUserData(accessToken, setUserRole, setUserID);
   }, [accessToken]);

   return (
      <Routes>
         <Route
            path="/"
            exact
            element={
               <ProtectedRoute accessToken={accessToken}>
                  <HomePage setAccessToken={setAccessToken} userRole={userRole} userID={userID} />
               </ProtectedRoute>
            }
         />
         <Route
            path="login"
            exact
            element={
               <NoToken accessToken={accessToken}>
                  <Login setAccessToken={setAccessToken} />
               </NoToken>
            }
         />
         <Route
            path="signup"
            exact
            element={
               <NoToken accessToken={accessToken}>
                  <SignUp />
               </NoToken>
            }
         />
         <Route
            path="forgot-password"
            exact
            element={
               <NoToken accessToken={accessToken}>
                  <ForgotPassword />
               </NoToken>
            }
         />
         <Route
            path="confirm-password"
            exact
            element={
               <NoToken accessToken={accessToken}>
                  <ConfirmPassword />
               </NoToken>
            }
         />
         <Route
            path="resend-email"
            exact
            element={
               <NoToken accessToken={accessToken}>
                  <ResendEmail />
               </NoToken>
            }
         />
         <Route
            path="admin-panel"
            exact
            element={
               <AdminRoute userRole={userRole} accessToken={accessToken}>
                  <AdminPanel userID={userID} />
               </AdminRoute>
            }
         />
         <Route
            path="user-info"
            exact
            element={
               <ProtectedRoute accessToken={accessToken}>
                  <UserInfo userID={userID} accessToken={accessToken} setAccessToken={setAccessToken} />
               </ProtectedRoute>
            }
         />
         <Route
            path="company-info"
            exact
            element={
               <ProtectedRoute accessToken={accessToken}>
                  <CompanyInfo />
               </ProtectedRoute>
            }
         />
         <Route path="user-info" exact element={<UserInfo userID={userID} />} />

         {/* Other Paths */}
         <Route path="*" element={<RedirectRoute accessToken={accessToken} />} />
      </Routes>
   );
};

export default App;
