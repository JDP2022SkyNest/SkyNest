import { createContext, useState } from "react";

const GlobalContext = createContext({});

export const GlobalProvider = ({ children }) => {
   const [accessToken, setAccessToken] = useState(localStorage.accessToken);
   const [userRole, setUserRole] = useState("");
   const [userID, setUserID] = useState("");
   const [moveFolderID, setMoveFilderID] = useState("");
   const [moveFileID, setMoveFileID] = useState("");

   return (
      <GlobalContext.Provider
         value={{
            accessToken,
            setAccessToken,
            userRole,
            setUserRole,
            userID,
            setUserID,
            moveFolderID,
            setMoveFilderID,
            moveFileID,
            setMoveFileID,
         }}
      >
         {children}
      </GlobalContext.Provider>
   );
};

export default GlobalContext;
