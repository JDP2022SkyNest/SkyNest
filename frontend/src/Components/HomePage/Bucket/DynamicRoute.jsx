import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { bucketContent } from "../../ReusableComponents/ReusableFunctions";
import NavbarPanel from "../../ReusableComponents/NavbarPanel";
import ROUTES from "../../Routes/ROUTES";
import Footer from "../../Footer/Footer";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetInfoMsg from "../../ReusableComponents/SetInfoMsg";
import AddFolderModal from "../Folder/AddFolderModal";
import Folders from "../Folder/Folders";
import UploadToBucket from "./UploadToBucket";
import Files from "../Files/Files";

const DynamicRoute = () => {
   const { routeId } = useParams();
   const [data, setData] = useState([]);
   const filteredFolders = data?.data?.folders.filter((el) => el.deletedOn !== null);
   const filteredFiles = data?.data?.files.filter((el) => el.deletedOn !== null);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [infoMsg, setInfoMsg] = useState("");
   const [loading, setLoading] = useState(true);
   const accessToken = localStorage.accessToken;
   const FolderLength = filteredFolders?.length;
   const FilesLength = filteredFiles?.length;

   useEffect(() => {
      const getData = async () => {
         await bucketContent(accessToken, routeId, setData, setErrorMsg);
         setLoading(false);
      };
      getData();
   }, [routeId, accessToken]);

   const refreshFoldersAndFiles = async () => {
      await bucketContent(accessToken, routeId, setData);
   };

   const allData = filteredFolders?.map((elem, index) => (
      <Folders elem={elem} key={index} setErrorMsg={setErrorMsg} setSuccessMsg={setSuccessMsg} refresh={refreshFoldersAndFiles} />
   ));

   const alLFiles = filteredFiles?.map((elem, index) => (
      <Files
         elem={elem}
         key={index}
         setErrorMsg={setErrorMsg}
         setSuccessMsg={setSuccessMsg}
         setInfoMsg={setInfoMsg}
         refresh={refreshFoldersAndFiles}
      />
   ));

   return (
      <div className="home-page-body">
         <NavbarPanel
            name={!loading ? `Folders: ${FolderLength} - Files: ${FilesLength}` : "Loading..."}
            searchBar={false}
            path={ROUTES.HOME}
            showName
         />
         <div className="container">
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="alert alert-danger text-danger text-center col-12 mt-3" />
            <SetSuccessMsg
               successMsg={successMsg}
               setSuccessMsg={setSuccessMsg}
               customStyle="alert alert-success text-success text-center col-12 mt-3"
            />
            <SetInfoMsg infoMsg={infoMsg} setInfoMsg={setInfoMsg} customStyle="alert alert-info text-info text-center col-12 mt-3" />
            <div className="py-2 mt-2 rounded d-flex">
               <AddFolderModal bucketId={data?.data?.bucketId} refresh={refreshFoldersAndFiles} />
               <UploadToBucket bucketId={data?.data?.bucketId} refresh={refreshFoldersAndFiles} />
            </div>
            <div>
               <div className="container data-folder mt-0">
                  {allData?.length > 0 && <div className="row mt-2">{allData}</div>}
                  {alLFiles?.length > 0 && <div className="my-2 mt-3 hr-devider" />}
                  <div className="row files">{alLFiles}</div>
               </div>
            </div>
         </div>
         <Footer />
      </div>
   );
};

export default DynamicRoute;
