import React, { useState } from "react";
import { Dropdown, Modal } from "react-bootstrap";
import * as BsCions from "react-icons/bs";
import * as TiCions from "react-icons/ti";
import * as AiCions from "react-icons/ai";
import { deleteBucket, restoreBucket, bucketInfoForLambda } from "../../ReusableComponents/ReusableFunctions";
import { useNavigate } from "react-router-dom";
import BucketInfo from "./BucketInfo";
import EditBucketModal from "./EditBucketModal";
import AllTags from "../Tags/AllTags";
import TagDisplay from "../Tags/TagDisplay";
import RemoveTag from "../Tags/RemoveTag";
import PermissionModal from "./Permissions/PermissionModal";

const Bucket = ({ elem, index, refreshBuckets, setErrorMsg, setSuccessMsg }) => {
   const [show, setShow] = useState(false);
   const handleClose = () => setShow(false);
   const handleShow = () => setShow(true);
   const navigate = useNavigate();
   const accessToken = localStorage.accessToken;
   const TGZ = elem?.tags;

   const writeTags = TGZ?.map((el, index) => {
      return <TagDisplay key={index} el={el} />;
   });

   return (
      <div className="col-12 col-sm-6 col-md-4 col-lg-3 p-1">
         <div
            key={index}
            className={`card custom-rounded bucket-hover cursor-pointer border-0 shadow ${
               elem.deletedOn !== null ? "deleted-clr" : "bg-white"
            } position-relative`}
         >
            <div
               onClick={() => {
                  navigate(`/bucket/${elem.bucketId}`, { replace: true });
               }}
               className="card-body p-2 px-3"
            >
               <div className="w-75 card-title text-overflow" style={{ fontSize: "18px" }}>
                  <TiCions.TiCloudStorageOutline className="cloud-icon-align mr-1" fill="var(--gold)" />
                  {elem.name}
               </div>
               <div className="text-muted text-overflow description-width">{elem.description}</div>
               <div className="w-100  text-overflow">
                  <small>
                     <AiCions.AiOutlineTag className="main-icon-align" />
                     {elem?.tags?.length > 0 ? <span className="ml-1">{writeTags}</span> : <span className="ml-1 text-muted">No tags</span>}
                  </small>
               </div>
            </div>
            {!elem.isPublic && <AiCions.AiFillLock className="private-bucket-indicator" />}
            <div>
               <Dropdown>
                  <Dropdown.Toggle>
                     <BsCions.BsThreeDotsVertical className="dots-icon" aria-expanded="false" />
                  </Dropdown.Toggle>
                  <Dropdown.Menu>
                     <Dropdown.Item
                        onClick={() => {
                           handleShow();
                           bucketInfoForLambda(accessToken, elem.bucketId);
                        }}
                        className="text-dark"
                     >
                        Bucket Info
                     </Dropdown.Item>
                     {elem.deletedOn === null && (
                        <Dropdown.Item className="text-dark">
                           <EditBucketModal refreshBuckets={refreshBuckets} elem={elem} />
                        </Dropdown.Item>
                     )}
                     {elem.deletedOn === null && (
                        <Dropdown.Item className="text-dark">
                           <PermissionModal objectId={elem.bucketId} />
                        </Dropdown.Item>
                     )}
                     {elem.deletedOn === null && (
                        <Dropdown.Item className="text-dark">
                           <AllTags objectId={elem.bucketId} refresh={refreshBuckets} TGZ={TGZ} setErrorMsg={setErrorMsg} />
                        </Dropdown.Item>
                     )}
                     {elem.deletedOn === null && TGZ.length > 0 ? (
                        <Dropdown.Item className="text-dark">
                           <RemoveTag TGZ={TGZ} objectId={elem.bucketId} refresh={refreshBuckets} />
                        </Dropdown.Item>
                     ) : null}
                     {elem.deletedOn === null ? (
                        <Dropdown.Item
                           onClick={async () => {
                              await deleteBucket(accessToken, elem.bucketId, setErrorMsg, setSuccessMsg);
                              refreshBuckets();
                           }}
                           className="text-danger"
                        >
                           Delete bucket
                        </Dropdown.Item>
                     ) : (
                        <Dropdown.Item
                           onClick={async () => {
                              await restoreBucket(accessToken, elem.bucketId, setErrorMsg, setSuccessMsg);
                              refreshBuckets();
                           }}
                           className="text-danger"
                        >
                           Restore bucket
                        </Dropdown.Item>
                     )}
                  </Dropdown.Menu>
               </Dropdown>
            </div>
         </div>
         <Modal onClick={(e) => e.stopPropagation()} show={show} onHide={handleClose} className="mt-3">
            <Modal.Body>
               <BucketInfo elem={elem} />
               <div className="mt-4 d-flex justify-content-end">
                  <button
                     onClick={(e) => {
                        e.preventDefault();
                        handleClose();
                     }}
                     className="ml-2 btn btn-secondary button-width"
                  >
                     Close
                  </button>
               </div>
            </Modal.Body>
         </Modal>
      </div>
   );
};

export default Bucket;
