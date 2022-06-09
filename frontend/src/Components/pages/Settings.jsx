import React from "react";
import { Link } from "react-router-dom";
import ROUTES from "../Routes/ROUTES";
import CenteredContainer from "../ReusableComponents/CenteredContainer";
import Label from "../ReusableComponents/Label";

function Settings() {
   return (
      <CenteredContainer>
         <form>
            <h2 className="text-center">SKY-NEST</h2>
            <p className="mb-4 p-0 text-center text-secondary">Edit your account</p>
            <div className="row">
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label id="firstNameInput">First Name</Label>
                     <input type="name" name="firstName" id="firstNameInput" className="form-control" required autoComplete="off" />
                  </div>
               </div>
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label id="lastNameInput">Last Name</Label>
                     <input type="name" name="lastName" id="lastNameInput" className="form-control" required autoComplete="off" />
                  </div>
               </div>
            </div>
            <div className="form-outline mb-1">
               <Label id="emailInput">Email adress</Label>
               <input type="email" name="email" id="emailInput" className="form-control" required autoComplete="off" />
            </div>
            <div className="form-outline mb-1">
               <Label id="phoneInput">Phone number</Label>
               <input type="number" name="phoneNumber" id="phoneInput" className="form-control no-arrow" required autoComplete="off" />
            </div>
            <div className="form-outline mb-1">
               <Label id="adressInput">Home adress</Label>
               <input type="text" name="adress" id="adressInput" className="form-control" required autoComplete="off" />
            </div>
            <div className="row">
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label id="passwordInput">Password</Label>
                     <div className="input-group">
                        <input name="password" id="passwordInput" className="form-control border-right-0" required autoComplete="off" />
                        <div className="input-group-prepend"></div>
                     </div>
                  </div>
               </div>
               <div className="col-md-6">
                  <div className="form-outline mb-1">
                     <Label id="confPasswordInput">Confirm password</Label>
                     <input name="confPassword" id="confPasswordInput" className="form-control" required autoComplete="off" />
                  </div>
               </div>
            </div>
            <div className="my-4"></div>
            <div className="mt-4 text-center">
               <p className="m-0">Already have an account? </p>
               <Link to={ROUTES.LOGIN} className="m-0 btn btn-link">
                  Login
               </Link>
            </div>
         </form>
      </CenteredContainer>
   );
}

export default Settings;
