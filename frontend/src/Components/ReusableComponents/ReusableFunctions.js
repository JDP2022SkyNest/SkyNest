import password from "secure-random-password";

export const passwordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
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

export const PasswordRequirements = (password, confPassword) => {
   return (
      <small>
         Password requirements:
         <ul className="mt-2 bt-2 text-danger unordered-list-padding">
            <li className={password.match(/([A-Z])/) ? "text-success" : ""}>Uppercase Letter</li>
            <li className={password.match(/([a-z])/) ? "text-success" : ""}>Lowercase Letter</li>
            <li className={password.match(/([\d])/) ? "text-success" : ""}>Number</li>
            <li className={password.length >= 8 ? "text-success" : ""}>Length of 8 characters or more</li>
            <li className={password === confPassword && password.length > 0 ? "text-success" : ""}>Passwords match</li>
         </ul>
      </small>
   );
};
