import React, { useState } from "react";
import AxiosInstance from "../../axios/AxiosInstance";
import { Modal } from "react-bootstrap";
import * as AiCions from "react-icons/ai";
import Tag from "./Tag";

const AllTags = ({ setErrorMsg }) => {
   const [show, setShow] = useState(false);
   const [data, setData] = useState();

   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const accessToken = localStorage.accessToken;

   const getAllTags = async () => {
      try {
         const response = await AxiosInstance.get("/tags", {
            headers: { Authorization: accessToken },
         });
         console.log(response.data);
         setData(response.data);
      } catch (err) {
         setErrorMsg(err.response.data.messages);
         console.log(err);
      }
   };

   return (
      <>
         <span
            onClick={async () => {
               handleShow();
               await getAllTags();
            }}
            className="ml-1s"
         >
            <AiCions.AiOutlinePlusCircle />
         </span>

         <Modal show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <Tag data={data} />
            </Modal.Body>
         </Modal>
      </>
   );
};

export default AllTags;
