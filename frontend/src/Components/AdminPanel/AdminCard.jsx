import React from "react";

const AdminCard = ({ title, body, color }) => {
   return (
      <div className={`card text-white bg-${color} my-1 my-sm-3`}>
         <div className="card-body">
            <h5 className="card-title">{title}</h5>
            <p className="card-text">{body}</p>
         </div>
      </div>
   );
};

export default AdminCard;
