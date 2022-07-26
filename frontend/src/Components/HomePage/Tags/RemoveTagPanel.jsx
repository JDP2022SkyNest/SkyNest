import React from "react";
import * as FaCions from "react-icons/fa";
import AxiosInstance from "../../axios/AxiosInstance";

const RemoveTagPanel = ({ TGZ, objectId, refresh, setErrorMsg, handleClose, setLoading }) => {
   const accessToken = localStorage.accessToken;

   const untagAnObject = async (tagId) => {
      try {
         await AxiosInstance.put(
            `/tags/${tagId}/object/${objectId}`,
            {},
            {
               headers: { Authorization: accessToken },
            }
         );
         refresh();
      } catch (err) {
         setErrorMsg(err.response.data.messages);
         console.log(err);
      }
   };

   const allTagData = TGZ?.map((el, index) => {
      return (
         <div className="container mt-2" key={index}>
            <div className="badge ml-2" style={{ backgroundColor: `#${el.rgb}`, fontSize: "13px" }}>
               {el.name}
            </div>
            <FaCions.FaWindowClose
               onClick={async () => {
                  setLoading(true);
                  await untagAnObject(el.id);
                  setLoading(false);
                  if (TGZ.length === 1) {
                     handleClose();
                  }
               }}
               className="float-right"
               style={{ color: "gray", position: "relative", top: "6px", cursor: "pointer" }}
            />
            {index !== TGZ.length - 1 && <hr />}
         </div>
      );
   });

   return <div>{allTagData}</div>;
};

export default RemoveTagPanel;
