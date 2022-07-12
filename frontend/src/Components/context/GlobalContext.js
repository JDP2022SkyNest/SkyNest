import { createContext, useState } from "react";

const GlobalContext = createContext({});

export const GlobalProvider = ({ children }) => {
   const [userCompany, setUserCompany] = useState();
   const [accessToken, setAccessToken] = useState(localStorage.accessToken);
   const [userRole, setUserRole] = useState("");
   const [userID, setUserID] = useState("");
   const [stateBucketId, setStateBucketId] = useState("");

   return (
      <GlobalContext.Provider
         value={{
            userCompany,
            setUserCompany,
            accessToken,
            setAccessToken,
            userRole,
            setUserRole,
            userID,
            setUserID,
            stateBucketId,
            setStateBucketId,
         }}
      >
         {children}
      </GlobalContext.Provider>
   );
};

export default GlobalContext;
