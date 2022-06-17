import password from "secure-random-password";
import AxiosInstance from "../axios/AxiosInstance";

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

export const getAllUsers = async (accessToken, stateToChange, messageToShow) => {
   try {
      let response = await AxiosInstance.get("/users", {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      if (err.response.status === 403) {
         messageToShow("Access token expired");
      } else {
         messageToShow(err.data.messages);
      }
   }
};

export const deleteUser = async (accessToken, id) => {
   try {
      await AxiosInstance.delete(`/users/${id}`, {
         headers: { Authorization: accessToken },
      });
      console.log("User Deleted");
   } catch (err) {
      console.log(err);
   }
};

export const emailVerification = async (token, success, error, info, setparams, resendEmail) => {
   info("Verifying in proggress");
   try {
      await AxiosInstance.get(`/public/confirm?token=${token}`);
      success("Email Verified");
   } catch (err) {
      if (err.response.status === 500) {
         success("Email already verified");
      } else if (err.response.status === 403) {
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

export const openFullscreen = () => {
   if (elem.requestFullscreen) {
      elem.requestFullscreen();
   } else if (elem.webkitRequestFullscreen) {
      elem.webkitRequestFullscreen();
   } else if (elem.msRequestFullscreen) {
      elem.msRequestFullscreen();
   }
};
