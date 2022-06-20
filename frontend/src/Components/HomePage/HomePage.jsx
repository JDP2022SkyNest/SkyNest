import React from "react";
import Footer from "../Footer/Footer";
import { Navbar, Container } from "react-bootstrap";
import { redirectTo } from "../ReusableComponents/ReusableFunctions";
import ROUTES from "../Routes/ROUTES";
import { useNavigate } from "react-router-dom";
import User from "./User";

const HomePage = ({ setAccessToken, userRole }) => {
   const navigate = useNavigate();
   return (
      <>
         <Navbar bg="dark" variant="dark">
            <Container>
               <Navbar.Brand className="text-white d-flex">
                  SKY-NEST <span className="ml-2 d-none d-md-block">/ Placeholder home page</span>
               </Navbar.Brand>
               <div className="d-flex">
                  <button
                     onClick={() => {
                        redirectTo(navigate, ROUTES.ADMIN, 1);
                     }}
                     className={userRole === "role_admin" ? `btn btn-danger mr-3` : "d-none"}
                  >
                     Admin Panel
                  </button>
                  <User setAccessToken={setAccessToken} />
               </div>
            </Container>
         </Navbar>
         <Footer />
      </>
   );
};

export default HomePage;
