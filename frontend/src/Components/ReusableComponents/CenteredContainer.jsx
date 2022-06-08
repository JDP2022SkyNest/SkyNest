import React from "react";
import { Container } from "react-bootstrap";

const CenteredContainer = ({ children }) => {
   return (
      <Container fluid className="min-vh-100 d-flex justify-content-center align-items-center color-latte">
         <div className="my-4 col-sm-10 col-md-7 col-lg-6 col-xl-4 py-5 px-3 px-sm-5 border login-form-radius shadow bg-white">{children}</div>
      </Container>
   );
};
export default CenteredContainer;
