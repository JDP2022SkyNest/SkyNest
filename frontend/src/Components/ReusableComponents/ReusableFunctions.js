import password from "secure-random-password";
import AxiosInstance from "../axios/AxiosInstance";
import jwt_decode from "jwt-decode";

// eslint-disable-next-line
export const passwordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d!@#&()\–\[{}\]:\-;',?|/*%~$_^+=<>\s]{8,50}/;
export const emailRegEx = /^[a-zA-Z0-9_+&*-]{1,64}(?:\.[a-zA-Z0-9_+&*-]+){0,64}@(?:[a-zA-Z0-9-]+\.){1,255}[a-zA-Z]{2,7}$/;
const elem = document.documentElement;

export const pwSuggestion = (length, func1, func2) => {
   let suggestedPw = password.randomPassword({ length, characters: [password.lower, password.upper, password.digits] });
   func1(suggestedPw);
   if (func2) {
      func2(suggestedPw);
   }
};

export const redirectTo = (func, path, delay) => {
   setTimeout(() => {
      func(path);
   }, delay);
};

export const getUserData = (accessToken, roleState, idState) => {
   if (accessToken) {
      const token = accessToken.slice(7);
      const decoded = jwt_decode(token);
      roleState(decoded.roles[0]);
      idState(decoded.uuid);
   }
};

export const getPersonalData = async (userID, accessToken, stateToChange, error) => {
   try {
      let response = await AxiosInstance.get(`/users/${userID}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error("Token Expired");
   }
};

export const getAllUsers = async (accessToken, stateToChange, messageToShow) => {
   try {
      let response = await AxiosInstance.get("/users", {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      if (err.response.status === 403) {
         messageToShow("Access denied");
      } else {
         messageToShow(err.response.data.messages);
      }
   }
};

export const editUserData = async (accessToken, id, payload, success, error, func) => {
   try {
      await AxiosInstance.put(
         `/users/${id}`,
         {
            name: payload.name,
            surname: payload.surname,
            phoneNumber: payload.phoneNumber,
            address: payload.address,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      error("");
      success("Profile Updated");
   } catch (err) {
      success("");
      if (err.response.status === 400) {
         error("Invalid Inputs");
      } else {
         error(err.response.data.messages);
      }
   }
   func();
};

export const disableUser = async (accessToken, id, error) => {
   try {
      await AxiosInstance.put(
         `/users/${id}/disable`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
   } catch (err) {
      error(err.response.data.messages);
   }
};

export const enableUser = async (accessToken, id, error) => {
   try {
      await AxiosInstance.put(
         `/users/${id}/enable`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
   } catch (err) {
      error(err.response.data.messages);
   }
};

export const emailVerification = async (accessToken, success, error, info, setparams, resendEmail) => {
   info("Verifying in proggress");
   try {
      await AxiosInstance.post(`/public/confirm?token=${accessToken}`);
      success("Email Verified");
   } catch (err) {
      if (err.response.status === 409) {
         success("Email already verified");
      } else if (err.response.status === 401) {
         error("Token expired");
         resendEmail(true);
      } else {
         error(err.response.data.messages);
         console.log(err.response.status);
      }
   }
   info("");
   setparams("");
};

export const onUserLogout = async (accessToken, stateToChange) => {
   try {
      await AxiosInstance.post(
         `/auth/logout`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
   } catch (err) {
      console.error("Token already expired", err);
   }
   localStorage.clear();
   stateToChange("");
};

export const promoteUser = async (accessToken, id, error, success) => {
   try {
      await AxiosInstance.put(
         `users/${id}/promote`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("User Promoted Successfully");
   } catch (err) {
      error(err.response.data.messages);
      console.error(err.response.status);
   }
};

export const demoteUser = async (accessToken, id, error, success) => {
   try {
      await AxiosInstance.put(
         `users/${id}/demote`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("User Demoted Successfully");
   } catch (err) {
      error(err.response.data.messages);
      console.error(err);
   }
};

export const getCompany = async (accessToken, stateToChange, error) => {
   try {
      let response = await AxiosInstance.get("/companies", {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.error(err);
   }
};

export const editCompany = async (accessToken, payload, error, success, func) => {
   try {
      await AxiosInstance.put(
         "/companies",
         {
            name: payload.name,
            address: payload.address,
            phoneNumber: payload.phoneNumber,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Company Info Changed");
   } catch (err) {
      error(err.response.data.messages);
      console.error(err);
   }
   func();
};

export const openFullscreen = () => {
   if (elem.requestFullscreen) {
      elem.requestFullscreen();
   } else if (elem.webkitRequestFullscreen) {
      elem.webkitRequestFullscreen();
   } else if (elem.msRequestFullscreen) {
      elem.msRequestFullscreen();
   }
};

export const alertTimeout = (delay, stateToChange) => {
   setTimeout(() => {
      stateToChange("");
   }, delay);
};
