import React, { useState } from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./Components/HomePage/HomePage";
import Login from "./Components/Login/Login";
import SignUp from "./Components/SignUp/SignUp";
import ProtectedRoute from "./Components/Routes/ProtectedRoute";
import RedirectRoute from "./Components/Routes/RedirectRoute";
import ForgotPassword from "./Components/ForgotPassword/ForgotPassword";
import ConfirmPassword from "./Components/ForgotPassword/ConfirmPassword";
import YourProfile from "./Components/pages/YourProfile";
import Settings from "./Components/pages/Settings";
import Logout from "./Components/pages/Logout";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";

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
         <Route path="/yourprofile" exact element={<YourProfile />} />
         <Route path="/settings" exact element={<Settings />} />
         <Route path="/logout" exact element={<Logout />} />
         <Route path="*" element={<RedirectRoute accessToken={accessToken} />} />
      </Routes>
   );
};

export default App;
