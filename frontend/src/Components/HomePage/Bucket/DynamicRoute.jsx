import React, { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { bucketContent, moveFolderRoot, moveFileRoot } from "../../ReusableComponents/ReusableFunctions";
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
import * as AiCions from "react-icons/ai";
import * as ImCions from "react-icons/im";
import GlobalContext from "../../context/GlobalContext";

const DynamicRoute = () => {
   const { routeId } = useParams();
   const [data, setData] = useState([]);
   const [searchTerm, setSearchTerm] = useState("");
   const [delState, setDelState] = useState(false);
   const filteredFolders = data?.data?.folders.filter(
      (el) => !!el.deletedOn === delState && (el.name.includes(searchTerm) || el.tags.find((x) => x.name.includes(searchTerm)))
   );
   const filteredFiles = data?.data?.files.filter(
      (el) => !!el.deletedOn === delState && (el.name.includes(searchTerm) || el.tags.find((x) => x.name.includes(searchTerm)))
   );
   const [errorMsg, setErrorMsg] = useState("");
   const [successMsg, setSuccessMsg] = useState("");
   const [infoMsg, setInfoMsg] = useState("");
   const [loading, setLoading] = useState(true);
   const accessToken = localStorage.accessToken;
   const FolderLength = filteredFolders?.length;
   const FilesLength = filteredFiles?.length;

   const { moveFolderID, setMoveFilderID, moveFileID, setMoveFileID } = useContext(GlobalContext);

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
            name={
               !loading ? (
                  <div>
                     <AiCions.AiFillFolderOpen className="main-icon-align" /> {FolderLength} - <AiCions.AiOutlineFile className="main-icon-align" />{" "}
                     {FilesLength}
                  </div>
               ) : (
                  "Loading..."
               )
            }
            searchBar={true}
            path={ROUTES.HOME}
            searchTerm={searchTerm}
            setSearchTerm={setSearchTerm}
            homeSearch
            setDelState={setDelState}
            placeholder="Search..."
         />
         <div className="container">
            <SetErrorMsg errorMsg={errorMsg} setErrorMsg={setErrorMsg} customStyle="alert alert-danger text-danger text-center col-12 mt-3" />
            <SetSuccessMsg
               successMsg={successMsg}
               setSuccessMsg={setSuccessMsg}
               customStyle="alert alert-success text-success text-center col-12 mt-3"
            />
            <SetInfoMsg infoMsg={infoMsg} setInfoMsg={setInfoMsg} customStyle="alert alert-info text-info text-center col-12 mt-3" close={false} />
            <div className="py-2 mt-2 rounded d-flex">
               <AddFolderModal bucketId={data?.data?.bucketId} refresh={refreshFoldersAndFiles} />
               <UploadToBucket bucketId={data?.data?.bucketId} refresh={refreshFoldersAndFiles} />
               {moveFolderID && (
                  <div
                     onClick={async () => {
                        await moveFolderRoot(accessToken, moveFolderID, setMoveFilderID, setErrorMsg, setSuccessMsg);
                        refreshFoldersAndFiles();
                     }}
                     className="ml-2 latte-background custom-rounded shadow"
                  >
                     <ImCions.ImPaste />
                  </div>
               )}
               {moveFileID && (
                  <div
                     onClick={async () => {
                        await moveFileRoot(accessToken, moveFileID, setMoveFileID, setErrorMsg, setSuccessMsg);
                        refreshFoldersAndFiles();
                     }}
                     className="ml-2 latte-background custom-rounded shadow"
                  >
                     <ImCions.ImPaste />
                  </div>
               )}
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
