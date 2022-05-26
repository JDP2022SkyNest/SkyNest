import React, { useState } from "react";
import HomePage from "./Components/HomePage/HomePage";
import Login from "./Components/Login/Login";

const App = () => {
	const [accessToken, setAccessToken] = useState(localStorage.accessToken);

	if (!accessToken) {
		return <Login setAccessToken={setAccessToken} />;
	}
	return <HomePage />;
};

export default App;
