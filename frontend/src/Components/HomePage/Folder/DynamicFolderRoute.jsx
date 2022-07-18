import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { folderContent } from "../../ReusableComponents/ReusableFunctions";
import NavbarPanel from "../../ReusableComponents/NavbarPanel";
import ROUTES from "../../Routes/ROUTES";
import Footer from "../../Footer/Footer";
import SetErrorMsg from "../../ReusableComponents/SetErrorMsg";
import SetSuccessMsg from "../../ReusableComponents/SetSuccessMsg";
import SetInfoMsg from "../../ReusableComponents/SetInfoMsg";
import AddFolderModal from "./AddFolderModal";
import Folders from "../Folder/Folders";
import UploadToFolder from "./UploadToFolder";
import Files from "../Files/Files";
import Breadcrumbs from "./Breadcrumbs";

const DynamicFolderRoute = () => {
   const { routeId } = useParams();
   const [data, setData] = useState([]);
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [infoMsg, setInfoMsg] = useState("");
   const accessToken = localStorage.accessToken;
   const FolderLength = data?.data?.folders.length;
   const FilesLength = data?.data?.files.length;

   const navigate = useNavigate();

   useEffect(() => {
      folderContent(accessToken, routeId, setData, setErrorMsg);
   }, [routeId, accessToken]);

   const refreshFoldersAndFiles = async () => {
      await folderContent(accessToken, routeId, setData);
   };

   const allData = data?.data?.folders.map((elem, index) => (
      <Folders elem={elem} key={index} setErrorMsg={setErrorMsg} setSuccessMsg={setSuccessMsg} refresh={refreshFoldersAndFiles} />
   ));

   const breadCrumb = data?.data?.path.map((elem, index) => {
      return <Breadcrumbs key={index} elem={elem} />;
   });

   const alLFiles = data?.data?.files.map((elem, index) => (
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
         <NavbarPanel name={`Folders: ${FolderLength} - Files: ${FilesLength}`} searchBar={false} path={ROUTES.HOME} />
         <div className="container">
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="alert alert-danger text-danger text-center col-12 mt-3" />
            <SetSuccessMsg
               successMsg={successMsg}
               setSuccessMsg={setSuccessMsg}
               customStyle="alert alert-success text-success text-center col-12 mt-3"
            />
            <SetInfoMsg infoMsg={infoMsg} setInfoMsg={setInfoMsg} customStyle="alert alert-info text-info text-center col-12 mt-3" />
            <small>
               <nav aria-label="breadcrumb">
                  <ol className="breadcrumb mt-3 mb-2 bg-white px-3 py-2 mx-1">
                     <li className="breadcrumb-item">
                        <button onClick={() => navigate(`/bucket/${data?.data?.bucketId}`, { replace: true })} className="btn-link border-0 bg-white">
                           Bucket
                        </button>
                     </li>
                     {breadCrumb}
                  </ol>
               </nav>
            </small>
            <div className="py-2 mt-2 rounded d-flex">
               <AddFolderModal parentFolderId={routeId} bucketId={data?.data?.bucketId} refresh={refreshFoldersAndFiles} />
               <UploadToFolder folderId={routeId} refresh={refreshFoldersAndFiles} />
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

export default DynamicFolderRoute;
