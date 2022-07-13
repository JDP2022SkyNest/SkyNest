import React from "react";
import * as AiCions from "react-icons/ai";

const Files = ({ elem }) => {
   return (
      <div className="col-6 col-sm-6 col-md-3 col-lg-2 p-1 mt-2">
         <div className="cursor-pointer bg-white rounded">
            <div className="p-2 px-3">
               <div className="text-overflow">
                  <AiCions.AiOutlineFile className="main-icon-align mr-1" />
                  {elem.name}
               </div>
            </div>
         </div>
      </div>
   );
};

export default Files;
