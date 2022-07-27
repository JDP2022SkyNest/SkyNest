import React from "react";
import { Toast } from "react-bootstrap";

const ToastX = ({ toast, text = "Placeholder" }) => {
   return (
      <>
         <Toast show={toast} style={{ position: "absolute", bottom: "50px", right: "10px" }}>
            <Toast.Header>
               <img src="holder.js/20x20?text=%20" className="rounded me-2" alt="" />
               <strong className="me-auto">Sky-Nest</strong>
               <small>3 secs ago</small>
            </Toast.Header>
            <Toast.Body>{text}</Toast.Body>
         </Toast>
      </>
   );
};

export default ToastX;
