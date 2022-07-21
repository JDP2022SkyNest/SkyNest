import React, { useContext } from "react";
import * as AiIcons from "react-icons/ai";
import * as CgIcons from "react-icons/cg";
import * as BiIcons from "react-icons/bi";
import { DropdownButton, Dropdown } from "react-bootstrap";
import { openFullscreen, redirectTo, onUserLogout } from "../../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";
import ChangePassword from "./ChangePassword";
import ROUTES from "../../Routes/ROUTES";
import ROLE from "../../Roles/Roles";
import "./Profile.css";
import InviteUser from "./InviteUser";
import GlobalContext from "../../context/GlobalContext";

const Profile = ({ setAccessToken, userID }) => {
   const accessToken = localStorage.accessToken;
   const { userRole } = useContext(GlobalContext);

   const navigate = useNavigate();

   return (
      <DropdownButton
         align="end"
         title={<AiIcons.AiOutlineUser className="main-icon-align" />}
         id="dropdown-menu-align-end"
         variant="dark"
         menuVariant="dark"
      >
         <div className="dropdown-menu-admin">
            <Dropdown.Item
               onClick={() => {
                  redirectTo(navigate, ROUTES.USERINFO, 1);
               }}
               className="mr-2"
               eventKey="1"
            >
               <CgIcons.CgProfile className="icons-align mr-2" />
               Your Profile
            </Dropdown.Item>
            <Dropdown.Divider />
            {userRole === ROLE.ADMIN && (
               <>
                  <Dropdown.Item className="mr-2" eventKey="1">
                     <CgIcons.CgProfile className="icons-align mr-2" />
                     <InviteUser />
                  </Dropdown.Item>
                  <Dropdown.Divider />
               </>
            )}

            <Dropdown.Item
               onClick={() => {
                  redirectTo(navigate, ROUTES.COMPANYINFO, 1);
               }}
               className="mr-2"
               eventKey="2"
            >
               <BiIcons.BiBuildingHouse className="icons-align mr-2" />
               Company Info
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item eventKey="3">
               <ChangePassword userID={userID} />
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item onClick={openFullscreen} eventKey="4">
               <AiIcons.AiOutlineFullscreen className="icons-align mr-2" />
               Fullscreen
            </Dropdown.Item>
            <Dropdown.Divider />
            <Dropdown.Item
               onClick={() => {
                  onUserLogout(accessToken, setAccessToken);
               }}
               eventKey="5"
            >
               <div>
                  <CgIcons.CgLogOut className="icons-align mr-2" />
                  Logout
               </div>
            </Dropdown.Item>
         </div>
      </DropdownButton>
   );
};

export default Profile;
