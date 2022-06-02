import password from "secure-random-password";
import { useNavigate } from "react-router-dom";

export const pwSuggestion = (length, func1, func2) => {
   let suggestedPw = password.randomPassword({ length, characters: [password.lower, password.upper, password.digits] });
   func1(suggestedPw);
   if (func2) {
      func2(suggestedPw);
   }
};

export const redirect = (path, delay) => {
   setTimeout(() => {
      useNavigate(path);
   }, delay);
};
