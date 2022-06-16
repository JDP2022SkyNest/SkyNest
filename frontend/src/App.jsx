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
         <Route path="login" exact element={<Login setAccessToken={setAccessToken} />} />
         <Route path="signup" exact element={<SignUp />} />
         <Route path="forgot-password" exact element={<ForgotPassword />} />
         <Route path="confirm-password" exact element={<ConfirmPassword />} />
         <Route path="resend-email" exact element={<ResendEmail />} />
         <Route path="admin-panel" exact element={<AdminPanel />} />

         {/* Other Paths */}
         <Route path="*" element={<RedirectRoute accessToken={accessToken} />} />
      </Routes>
   );
};

export default App;
