import React, { useState } from "react";
import { Routes, Route } from "react-router-dom";
import HomePage from "./Components/HomePage/HomePage";
import Login from "./Components/Login/Login";
import SignUp from "./Components/SignUp/SignUp";
import ProtectedRoute from "./Components/Routes/ProtectedRoute";
import RedirectRoute from "./Components/Routes/RedirectRoute";

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

         {/* Other Paths */}
         <Route path="*" element={<RedirectRoute accessToken={accessToken} />} />
      </Routes>
   );
};

export default App;
