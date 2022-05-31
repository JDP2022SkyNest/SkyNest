import { useReducer } from "react";
import { Link } from "react-router-dom";
import ROUTES from "../Routes/ROUTES";
import "./SignUp.css";

const initialState = {
  firstName: "",
  lastName: "",
  email: "",
  phoneNumber: "",
  adress: "",
  password: "",
  confPassword: "",
};

const RegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

function reduser(state, action) {
  return { ...state, [action.input]: action.value };
}
function validateState(state) {
  return state.password === state.confPassword && state.password.length > 8;
}

const SignUp = () => {
  const [state, dispatch] = useReducer(reduser, initialState);

  function handleOnSubmit(e) {
    e.preventDefault();
    if (state.password.match(RegEx)) {
      console.log("Success");
    } else {
      console.log("Failed");
    }
  }

  function onChange(e) {
    const action = {
      input: e.target.name,
      value: e.target.value,
    };
    dispatch(action);
  }

  return (
    <div className="vh-100 container-fluid d-flex justify-content-center align-items-center  latte">
      <div className="col-sm-10 col-md-7 col-lg-6 col-xl-4 px-5 py-4 border login-form-radius shadow bg-white">
        <form>
          <h2 className="text-center">SKY-NEST</h2>
          <p className="mb-4 p-0 text-center text-secondary">Create your account</p>
          <p className="alert alert-danger text-danger text-center d-none"></p>
          <div className="row">
            <div className="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="name"
                  name="firstName"
                  id="firstNameInput"
                  onChange={onChange}
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="firstNameInput">
                  First Name <span className="text-danger">*</span>
                </label>
              </div>
            </div>
            <div className="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="name"
                  name="lastName"
                  onChange={onChange}
                  id="lastNameInput"
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="lastNameInput">
                  Last Name <span className="text-danger">*</span>
                </label>
              </div>
            </div>
          </div>
          <div className="form-outline mb-1">
            <input
              type="email"
              name="email"
              onChange={onChange}
              id="emailInput"
              className="form-control form-control-lg"
              required
              autoComplete="off"
            />
            <label className="form-label" htmlFor="emailInput">
              Email address <span className="text-danger">*</span>
            </label>
          </div>
          <div className="form-outline mb-1">
            <input
              type="number"
              name="phoneNumber"
              onChange={onChange}
              id="phoneInput"
              className="form-control form-control-lg"
              required
              autoComplete="off"
            />
            <label className="form-label" htmlFor="phoneInput">
              Phone number <span className="text-danger">*</span>
            </label>
          </div>
          <div className="form-outline mb-1">
            <input
              type="text"
              name="adress"
              onChange={onChange}
              id="adressInput"
              className="form-control form-control-lg"
              required
              autoComplete="off"
            />
            <label className="form-label" htmlFor="adressInput">
              Adress <span className="text-danger">*</span>
            </label>
          </div>
          <div className="row">
            <div className="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="password"
                  name="password"
                  onChange={onChange}
                  id="passwordInput"
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="passwordInput">
                  Password <span className="text-danger">*</span>
                </label>
              </div>
            </div>
            <div className="col-md-6">
              <div className="form-outline mb-1">
                <input
                  type="password"
                  name="confPassword"
                  onChange={onChange}
                  id="confPasswordInput"
                  className="form-control form-control-lg"
                  required
                  autoComplete="off"
                />
                <label className="form-label" htmlFor="confPasswordInput">
                  Confirm Password <span className="text-danger">*</span>
                </label>
              </div>
            </div>
          </div>
          <div className="pt-1 mb-1">
            <button
              onClick={handleOnSubmit}
              className={!validateState(state) ? "btn btn-dark btn-lg btn-block disabled" : "btn btn-dark btn-lg btn-block"}
            >
              Sign Up
            </button>
          </div>
          <div className="mt-4 text-center">
            <p className="m-0">Already have an account? </p>
            <Link to={ROUTES.LOGIN} href="#!" className="m-0 btn btn-link">
              Login
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default SignUp;
