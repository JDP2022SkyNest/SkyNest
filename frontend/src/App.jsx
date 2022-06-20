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
import NoToken from "./Components/Routes/NoToken";

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
