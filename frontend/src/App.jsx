import React, { useState } from "react";
import HomePage from "./components/HomePage/HomePage";
import Loader from "./components/LoadingScreen/Loader";
import LoginPage from "./components/Login/LoginPage";

function App() {
	const [fakeToken, setFakeToken] = useState(localStorage.accessToken);
	const [fakeData, setFakeData] = useState("");

	if (fakeToken) {
		if (fakeData) {
			return <HomePage setFakeToken={setFakeToken} fakeData={fakeData} setFakeData={setFakeData} />;
		}
		return <Loader setFakeData={setFakeData} />;
	} else {
		return <LoginPage setFakeToken={setFakeToken} />;
	}
}

export default App;
