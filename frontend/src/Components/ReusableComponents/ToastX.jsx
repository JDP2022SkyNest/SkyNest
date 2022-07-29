import React from "react";
import { useState } from "react";
import { Toast } from "react-bootstrap";

const ToastX = ({ toast, text = "Placeholder" }) => {
   const [show, setShow] = useState(toast);
   return (
      <>
         <Toast onClose={() => setShow(false)} show={show} delay={2000} autohide style={{ position: "fixed", bottom: "50px", right: "10px" }}>
            <Toast.Header>
               <img src="holder.js/20x20?text=%20" className="rounded me-2" alt="" />
               <strong className="me-auto">Sky-Nest</strong>
               <small>3 secs ago</small>
            </Toast.Header>
            <Toast.Body className="ml-2">{text}</Toast.Body>
         </Toast>
      </>
   );
};

export default ToastX;
