import React, { useState } from "react";
import HomePage from "./components/HomePage/HomePage";
import Loader from "./components/LoadingScreen/Loader";
import LoginPage from "./components/Login/LoginPage";

function App() {
	// Fake dummy Values that will be changed
	const [fakeToken, setFakeToken] = useState(localStorage.accessToken);
	const [fakeData, setFakeData] = useState("");

	// 1- If users logged in the past and have token in LS
	if (fakeToken) {
		// 3- When Data gets Loaded we go to Home Page.
		if (fakeData) {
			return <HomePage setFakeToken={setFakeToken} fakeData={fakeData} setFakeData={setFakeData} />;
		}
		// 2 - If logged in, Loader's function is to show loading screen while Loading data
		return <Loader setFakeData={setFakeData} />;
	} else {
		// If Access Token isn't generated Login Page Will be Shown
		return <LoginPage setFakeToken={setFakeToken} />;
	}
}

export default App;
