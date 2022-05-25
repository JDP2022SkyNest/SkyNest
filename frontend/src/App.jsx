import React, { useState } from "react";
import HomePage from "./components/HomePage/HomePage";
import SignUpPage from "./components/SignUp/SignUpPage";
import LoginPage from "./components/Login/LoginPage";
import { Routes, Route } from "react-router-dom";
import ProtectedRoute from "./components/Routes/ProtectedRoute";
import RedirectRoute from "./components/Routes/RedirectRoute";

function App() {
	const [accessToken, setAccessToken] = useState(localStorage.accessToken);

	return (
		<Routes>
			<Route path="/" exact element={<RedirectRoute accessToken={accessToken} />} />
			<Route path="login" exact element={<LoginPage setAccessToken={setAccessToken} />} />
			<Route path="signup" exact element={<SignUpPage />} />
			<Route
				path="homepage"
				exact
				element={
					<ProtectedRoute accessToken={accessToken}>
						<HomePage setAccessToken={setAccessToken} />
					</ProtectedRoute>
				}
			/>

			{/* CATCH ALL PATH */}
			<Route path="*" element={<RedirectRoute accessToken={accessToken} />} />
		</Routes>
	);
}

export default App;
