import password from "secure-random-password";
export const RegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

export const pwSuggestion = (length, func1, func2) => {
   let suggestedPw = password.randomPassword({ length, characters: [password.lower, password.upper, password.digits] });
   func1(suggestedPw);
   if (func2) {
      func2(suggestedPw);
   }
};
