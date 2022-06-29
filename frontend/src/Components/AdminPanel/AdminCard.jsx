import React from "react";

const AdminCard = ({ title, body, color, centered = false }) => (
   <div className={`card text-dark bg-${color} my-3 my-sm-3 shadow`}>
      <div className="card-body px-3 py-2">
         <h5 className={`card-title-style ${centered}`}>{title}</h5>
         <span className={`card-text ${centered}`}>{body}</span>
      </div>
   </div>
);

export default AdminCard;
