import React, { useState } from "react";
import HomePage from "./components/HomePage/HomePage";
import Loader from "./components/LoadingScreen/Loader";
import LoginPage from "./components/Login/LoginPage";

function App() {
	// Fake dummy Values that will be changed
	const [accessToken, setAccessToken] = useState(localStorage.accessToken);
	const [userData, setUserData] = useState("");

	// 1- If users logged in the past and have token in LS
	if (accessToken) {
		// 3- When Data gets Loaded we go to Home Page.
		if (userData) {
			return <HomePage setAccessToken={setAccessToken} userData={userData} setUserData={setUserData} />;
		}
		// 2 - If logged in, Loader's function is to show loading screen while Loading data
		return <Loader setUserData={setUserData} />;
	}
	return <LoginPage setAccessToken={setAccessToken} />;
}

export default App;
