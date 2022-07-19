import React from "react";
import { useNavigate } from "react-router-dom";

const Breadcrumbs = ({ elem }) => {
   const navigate = useNavigate();

   return (
      <li className="breadcrumb-item">
         <button onClick={() => navigate(`/folder/${elem.id}`, { replace: true })} className="btn-link border-0 bg-white breadcrumb-other">
            {elem.name}
         </button>
      </li>
   );
};

export default Breadcrumbs;
