import React from "react";
import { Container } from "react-bootstrap";

const CenteredContainer = ({ children }) => {
   return (
      <Container fluid className="vh-100  d-flex justify-content-center align-items-center color-latte">
         <div className="col-sm-10 col-md-7 col-lg-6 col-xl-4 p-5 border login-form-radius shadow bg-white">{children}</div>
      </Container>
   );
};
export default CenteredContainer;
