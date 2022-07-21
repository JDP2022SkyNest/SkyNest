import React from "react";
import AxiosInstance from "../../axios/AxiosInstance";

const AllTags = ({ setErrorMsg }) => {
   const accessToken = localStorage.accessToken;

   const getAllTags = async () => {
      try {
         const response = await AxiosInstance.get("/tags", {
            headers: { Authorization: accessToken },
         });
         console.log(response);
      } catch (err) {
         setErrorMsg(err.response.data.messages);
         console.log(err);
      }
   };

   return <div onClick={() => getAllTags()}>Tags</div>;
};

export default AllTags;
