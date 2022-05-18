import React, { useState } from "react";
import HomePage from "./components/HomePage/HomePage";
import Loader from "./components/LoadingScreen/Loader";
import LoginPage from "./components/Login/LoginPage";

function App() {
	const [fakeToken, setFakeToken] = useState(localStorage.accessToken);
	const [fakeData, setFakeData] = useState("FAKE_DATA");

	if (fakeToken) {
		if (fakeData) {
			return <HomePage setFakeToken={setFakeToken} />;
		}
		return <Loader />;
	} else {
		return <LoginPage setFakeToken={setFakeToken} />;
	}
}

export default App;
