import password from "secure-random-password";

export const passwordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
export const emailRegEx = /^[a-zA-Z0-9_+&*-]{1,64}(?:\.[a-zA-Z0-9_+&*-]+){0,64}@(?:[a-zA-Z0-9-]+\.){1,255}[a-zA-Z]{2,7}$/;

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
