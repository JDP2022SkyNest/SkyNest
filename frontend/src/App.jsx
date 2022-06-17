import React, { useState } from "react";
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
import NoTokenRoute from "./Components/Routes/NoTokenRoute";

const App = () => {
   const [accessToken, setAccessToken] = useState(localStorage.accessToken);

   return (
      <Routes>
         <Route
            path="/"
            exact
            element={
               <ProtectedRoute accessToken={accessToken}>
                  <HomePage setAccessToken={setAccessToken} />
               </ProtectedRoute>
            }
         />
         <Route
            path="login"
            exact
            element={
               <NoTokenRoute accessToken={accessToken}>
                  <Login setAccessToken={setAccessToken} />
               </NoTokenRoute>
            }
         />
         <Route
            path="signup"
            exact
            element={
               <NoTokenRoute accessToken={accessToken}>
                  <SignUp />
               </NoTokenRoute>
            }
         />
         <Route
            path="forgot-password"
            exact
            element={
               <NoTokenRoute accessToken={accessToken}>
                  <ForgotPassword />
               </NoTokenRoute>
            }
         />
         <Route
            path="confirm-password"
            exact
            element={
               <NoTokenRoute accessToken={accessToken}>
                  <ConfirmPassword />
               </NoTokenRoute>
            }
         />
         <Route
            path="resend-email"
            exact
            element={
               <NoTokenRoute accessToken={accessToken}>
                  <ResendEmail />
               </NoTokenRoute>
            }
         />
         <Route
            path="admin-panel"
            exact
            element={
               <ProtectedRoute accessToken={accessToken}>
                  <AdminPanel />
               </ProtectedRoute>
            }
         />

         {/* Other Paths */}
         <Route path="*" element={<RedirectRoute accessToken={accessToken} />} />
      </Routes>
   );
};

export default App;
