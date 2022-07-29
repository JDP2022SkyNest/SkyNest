import React, { useEffect, useContext } from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./Components/HomePage/HomePage";
import Login from "./Components/Login/Login";
import SignUp from "./Components/SignUp/SignUp";
import ProtectedRoute from "./Components/Routes/ProtectedRoute";
import RedirectRoute from "./Components/Routes/RedirectRoute";
import ForgotPassword from "./Components/ForgotPassword/ForgotPassword";
import ConfirmPassword from "./Components/ForgotPassword/ConfirmPassword";
import AdminPanel from "./Components/AdminPanel/AdminPanel";
import NoToken from "./Components/Routes/NoToken";
import AdminRoute from "./Components/Routes/AdminRoute";
import { getUserData } from "./Components/ReusableComponents/ReusableFunctions";
import UserInfo from "./Components/UserInfo/UserInfo";
import CompanyInfo from "./Components/CompanyInfo/CompanyInfo";
import GlobalContext from "./Components/context/GlobalContext";
import DynamicRoute from "./Components/HomePage/Bucket/DynamicRoute";
import DynamicFolderRoute from "./Components/HomePage/Folder/DynamicFolderRoute";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import TagRoute from "./Components/HomePage/Tags/TagRoute";

const App = () => {
   const { accessToken, setUserID, setUserRole } = useContext(GlobalContext);

   useEffect(() => {
      if (accessToken) {
         getUserData(accessToken, setUserRole, setUserID);
      }
      // eslint-disable-next-line
   }, [accessToken]);

   return (
      <Routes>
         <Route
            path="/"
            exact
            element={
               <ProtectedRoute>
                  <HomePage />
               </ProtectedRoute>
            }
         />
         <Route
            path="bucket/:routeId"
            exact
            element={
               <ProtectedRoute>
                  <DynamicRoute />
               </ProtectedRoute>
            }
         />
         <Route
            path="folder/:routeId"
            exact
            element={
               <ProtectedRoute>
                  <DynamicFolderRoute />
               </ProtectedRoute>
            }
         />
         <Route
            path="tags/:routeId"
            exact
            element={
               <ProtectedRoute>
                  <TagRoute />
               </ProtectedRoute>
            }
         />
         <Route
            path="login"
            exact
            element={
               <NoToken>
                  <Login />
               </NoToken>
            }
         />
         <Route
            path="signup"
            exact
            element={
               <NoToken>
                  <SignUp />
               </NoToken>
            }
         />
         <Route
            path="forgot-password"
            exact
            element={
               <NoToken>
                  <ForgotPassword />
               </NoToken>
            }
         />
         <Route
            path="confirm-password"
            exact
            element={
               <NoToken>
                  <ConfirmPassword />
               </NoToken>
            }
         />
         <Route
            path="admin-panel"
            exact
            element={
               <AdminRoute>
                  <AdminPanel />
               </AdminRoute>
            }
         />
         <Route
            path="user-info"
            exact
            element={
               <ProtectedRoute>
                  <UserInfo />
               </ProtectedRoute>
            }
         />
         <Route
            path="company-info"
            exact
            element={
               <ProtectedRoute>
                  <CompanyInfo />
               </ProtectedRoute>
            }
         />
         <Route path="user-info" exact element={<UserInfo />} />

         {/* Other Paths */}
         <Route path="*" element={<RedirectRoute />} />
      </Routes>
   );
};

export default App;
