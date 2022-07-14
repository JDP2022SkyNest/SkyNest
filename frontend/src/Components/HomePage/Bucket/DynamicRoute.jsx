import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { bucketContent } from "../../ReusableComponents/ReusableFunctions";
import NavbarPanel from "../../ReusableComponents/NavbarPanel";
import ROUTES from "../../Routes/ROUTES";
import Footer from "../../Footer/Footer";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import AddFolderModal from "../Folder/AddFolderModal";
import Folders from "../Folder/Folders";
import UploadToBucket from "./UploadToBucket";
import Files from "../Files";

const DynamicRoute = () => {
   const { routeId } = useParams();
   const [data, setData] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const accessToken = localStorage.accessToken;
   const FolderLength = data?.data?.folders.length;
   const FilesLength = data?.data?.files.length;

   useEffect(() => {
      bucketContent(accessToken, routeId, setData);
   }, [routeId, accessToken]);

   const refreshFoldersAndFiles = async () => {
      await bucketContent(accessToken, routeId, setData);
   };

   const allData = data?.data?.folders.map((elem, index) => (
      <Folders elem={elem} key={index} setErrorMsg={setErrorMsg} setSuccessMsg={setSuccessMsg} refresh={refreshFoldersAndFiles} />
   ));

   const alLFiles = data?.data?.files.map((elem, index) => <Files elem={elem} key={index} />);

   return (
      <div className="home-page-body">
         <NavbarPanel name={`Folders: ${FolderLength} - Files: ${FilesLength}`} searchBar={false} path={ROUTES.HOME} />
         <div className="container">
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="alert alert-danger text-danger text-center col-12 mt-3" />
            <SetSuccessMsg
               successMsg={successMsg}
               setSuccessMsg={setSuccessMsg}
               customStyle="alert alert-success text-success text-center col-12 mt-3"
            />
            <div className="py-2 mt-2 mb-1 rounded d-flex">
               <AddFolderModal bucketId={data?.data?.bucketId} refresh={refreshFoldersAndFiles} />
               <UploadToBucket bucketId={data?.data?.bucketId} refresh={refreshFoldersAndFiles} />
            </div>
            <div>
               <div className="container data-folder">
                  <div className="row">{allData}</div>
                  <div className="row">{alLFiles}</div>
               </div>
            </div>
         </div>
         <Footer />
      </div>
   );
};

export default DynamicRoute;
