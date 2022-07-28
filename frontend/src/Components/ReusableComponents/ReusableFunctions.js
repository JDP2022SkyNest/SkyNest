import password from "secure-random-password";
import AxiosInstance from "../axios/AxiosInstance";
import jwt_decode from "jwt-decode";

// eslint-disable-next-line
export const passwordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d!@#&()\–\[{}\]:\-;',?|/*%~$_^+=<>\s]{8,50}/;
export const emailRegEx = /^[a-zA-Z0-9_+&*-]{1,64}(?:\.[a-zA-Z0-9_+&*-]+){0,64}@(?:[a-zA-Z0-9-]+\.){1,255}[a-zA-Z]{2,7}$/;
const elem = document.documentElement;

export const pwSuggestion = (length, func1, func2) => {
   let suggestedPw = password.randomPassword({ length, characters: [password.lower, password.upper, password.digits] });
   func1(suggestedPw);
   if (func2) {
      func2(suggestedPw);
   }
};

export const redirectTo = (func, path, delay) => {
   setTimeout(() => {
      func(path);
   }, delay);
};

export const getUserData = (accessToken, roleState, idState) => {
   if (accessToken) {
      const token = accessToken.slice(7);
      const decoded = jwt_decode(token);
      roleState(decoded.roles[0]);
      idState(decoded.uuid);
   }
};

export const getPersonalData = async (userID, accessToken, stateToChange, error) => {
   try {
      let response = await AxiosInstance.get(`/users/${userID}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error("Token Expired");
   }
};

export const getAllUsers = async (accessToken, stateToChange, messageToShow) => {
   try {
      let response = await AxiosInstance.get("/users", {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      if (err.response.status === 403) {
         messageToShow("Access denied");
      } else {
         messageToShow(err.response.data.messages);
      }
   }
};

export const editUserData = async (accessToken, id, payload, success, error, func) => {
   try {
      await AxiosInstance.put(
         `/users/${id}`,
         {
            name: payload.name,
            surname: payload.surname,
            phoneNumber: payload.phoneNumber,
            address: payload.address,
            positionInCompany: payload.positionInCompany,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      error("");
      success("Profile Updated");
   } catch (err) {
      success("");
      if (err.response.status === 400) {
         error("Invalid Inputs");
      } else {
         error(err.response.data.messages);
      }
   }
   func();
};

export const disableUser = async (accessToken, id, error) => {
   try {
      await AxiosInstance.put(
         `/users/${id}/disable`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
   } catch (err) {
      error(err.response.data.messages);
   }
};

export const enableUser = async (accessToken, id, error) => {
   try {
      await AxiosInstance.put(
         `/users/${id}/enable`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
   } catch (err) {
      error(err.response.data.messages);
   }
};

export const emailVerification = async (accessToken, success, error, info, setparams) => {
   info("Verifying in proggress");
   try {
      await AxiosInstance.post(`/public/confirm?token=${accessToken}`);
      success("Email Verified");
   } catch (err) {
      if (err.response.status === 409) {
         success("Email is verified");
      } else if (err.response.status === 401) {
         error("Token expired");
      } else {
         error(err.response.data.messages);
         console.log(err.response.status);
      }
   }
   info("");
   setparams("");
};

export const onUserLogout = async (accessToken, stateToChange) => {
   const token = accessToken;
   localStorage.clear();
   stateToChange("");
   try {
      await AxiosInstance.post(
         `/auth/logout`,
         {},
         {
            headers: { Authorization: token },
         }
      );
   } catch (err) {
      console.error("Token already expired", err);
   }
};

export const promoteUser = async (accessToken, id, error, success) => {
   try {
      await AxiosInstance.put(
         `users/${id}/promote`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("User Promoted Successfully");
   } catch (err) {
      error(err.response.data.messages);
      console.error(err.response.status);
   }
};

export const demoteUser = async (accessToken, id, error, success) => {
   try {
      await AxiosInstance.put(
         `users/${id}/demote`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("User Demoted Successfully");
   } catch (err) {
      error(err.response.data.messages);
      console.error(err);
   }
};

export const getCompany = async (accessToken, stateToChange, error, info = true) => {
   try {
      let response = await AxiosInstance.get("/companies", {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      if (info) {
         error(err.response.data.messages);
         console.error(err);
      }
   }
};

export const editCompany = async (accessToken, payload, error, success, func) => {
   try {
      await AxiosInstance.put(
         "/companies",
         {
            name: payload.name,
            address: payload.address,
            phoneNumber: payload.phoneNumber,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Company Info Changed");
   } catch (err) {
      const message = err.response.data.messages[0];
      const finalMsg = message[0].toUpperCase() + message.slice(1);
      error(finalMsg);
      console.error(err);
   }
   func();
};

export const addToCompany = async (accessToken, id, error, success) => {
   try {
      await AxiosInstance.put(
         `/users/${id}/company/add`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("User added to your company");
   } catch (err) {
      error(err.response.data.messages);
      console.error(err);
   }
};

export const getAllBuckets = async (accessToken, stateToChange, error) => {
   try {
      const response = await AxiosInstance.get("/buckets", {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const deleteBucket = async (accessToken, bucketId, error, success) => {
   try {
      await AxiosInstance.put(
         `/buckets/${bucketId}/delete`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Bucket Successfully Deleted");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const restoreBucket = async (accessToken, bucketId, error, success) => {
   try {
      await AxiosInstance.put(
         `/buckets/${bucketId}/restore`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Bucket Successfully Restored");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const deleteFolder = async (accessToken, folderId, error, success) => {
   try {
      await AxiosInstance.put(
         `/folders/delete/${folderId}`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Folder Successfully Deleted");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const restoreFolder = async (accessToken, folderId, error, success) => {
   try {
      await AxiosInstance.put(
         `/folders/${folderId}/restore`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Folder Successfully Restored");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const deleteFile = async (accessToken, fileId, error, success) => {
   try {
      await AxiosInstance.delete(`/files/${fileId}`, {
         headers: { Authorization: accessToken },
      });
      success("File Successfully Deleted");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const restoreFile = async (accessToken, fileId, error, success) => {
   try {
      await AxiosInstance.put(
         `/files/${fileId}/restore`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("File Successfully Restored");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const bucketContent = async (accessToken, bucketId, stateToChange, error) => {
   try {
      let response = await AxiosInstance.get(`/buckets/${bucketId}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const folderContent = async (accessToken, folderId, stateToChange, error) => {
   try {
      let response = await AxiosInstance.get(`/folders/${folderId}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const moveFolder = async (accessToken, fileId, folderId, stateToChange, error, success) => {
   try {
      await AxiosInstance.put(
         `/folders/${fileId}/move/${folderId}`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Folder Successfully Moved");
      stateToChange("");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const moveFolderRoot = async (accessToken, fileId, stateToChange, error, success) => {
   try {
      await AxiosInstance.put(
         `/folders/${fileId}/move`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Folder Successfully Moved");
      stateToChange("");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const moveFile = async (accessToken, fileId, folderId, stateToChange, error, success) => {
   try {
      await AxiosInstance.put(
         `/files/${fileId}/move/${folderId}`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("File Successfully Moved");
      stateToChange("");
   } catch (err) {
      error(err.response.data.error);
      console.log(err);
   }
};

export const moveFileRoot = async (accessToken, fileId, stateToChange, error, success) => {
   try {
      await AxiosInstance.put(
         `/files/${fileId}/move`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Folder Successfully Moved");
      stateToChange("");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const fileDownload = async (accessToken, fileId, fileName, error, success) => {
   try {
      const response = await AxiosInstance.get(`/files/${fileId}`, {
         headers: { Authorization: accessToken },
         responseType: "blob",
      });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", fileName);
      document.body.appendChild(link);
      link.click();
      success("File Downloaded");
   } catch (err) {
      if (err.response.status === 401) {
         error("Invalid Session Token");
      } else if (err.response.status === 403) {
         error("User does not have access to bucket");
      } else if (err.response.status === 404) {
         error("File not found");
      } else if (err.response.status === 500) {
         error("Internal Server Error");
      }
   }
};

export const getAllTags = async (accessToken, stateToChange, error) => {
   try {
      const response = await AxiosInstance.get("/tags", {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const setTheTag = async (accessToken, tagId, objectId) => {
   try {
      await AxiosInstance.post(
         `/tags/${tagId}/object/${objectId}`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
   } catch (err) {
      console.log(err);
   }
};

export const AllBucketPermissions = async (accessToken, bucketId, stateToChange, error) => {
   try {
      const response = await AxiosInstance.get(`/permissions/bucket/${bucketId}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const AllFolderPermissions = async (accessToken, folderId, stateToChange, error) => {
   try {
      const response = await AxiosInstance.get(`/permissions/folder/${folderId}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const AllFilePermissions = async (accessToken, fileId, stateToChange, error) => {
   try {
      const response = await AxiosInstance.get(`/permissions/file/${fileId}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const GrantBucketPermission = async (accessToken, email, bucketId, role, error, success) => {
   try {
      await AxiosInstance.post(
         `/permissions/bucket`,
         {
            grantedToEmail: email,
            objectId: bucketId,
            access: role,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Permission Added");
   } catch (err) {
      if (err.response.status === 400) {
         error(err.response.data.messages);
         console.log(err);
      } else {
         error(err.response.data.messages);
         console.log(err);
      }
   }
};

export const GrantFolderPermission = async (accessToken, email, folderId, role, error, success) => {
   try {
      await AxiosInstance.post(
         `/permissions/folder`,
         {
            grantedToEmail: email,
            objectId: folderId,
            access: role,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Permission Added");
   } catch (err) {
      if (err.response.status === 400) {
         error(err.response.data.messages);
         console.log(err);
      } else {
         error(err.response.data.messages);
         console.log(err);
      }
   }
};

export const GrantFilePermission = async (accessToken, email, fileId, role, error, success) => {
   try {
      await AxiosInstance.post(
         `/permissions/file`,
         {
            grantedToEmail: email,
            objectId: fileId,
            access: role,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Permission Added");
   } catch (err) {
      if (err.response.status === 400) {
         error(err.response.data.messages);
         console.log(err);
      } else {
         error(err.response.data.messages);
         console.log(err);
      }
   }
};

export const RevokeBucketPermissions = async (accessToken, bucketId, userEmail, error, success) => {
   try {
      await AxiosInstance.delete(`/permissions/bucket`, {
         headers: { Authorization: accessToken },
         data: {
            grantedToEmail: userEmail,
            objectId: bucketId,
         },
      });
      success("Permission Removed");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const RevokeFolderPermissions = async (accessToken, folderId, userEmail, error, success) => {
   try {
      await AxiosInstance.delete(`/permissions/folder`, {
         headers: { Authorization: accessToken },
         data: {
            grantedToEmail: userEmail,
            objectId: folderId,
         },
      });
      success("Permission Removed");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const RevokeFilePermissions = async (accessToken, fileId, userEmail, error, success) => {
   try {
      await AxiosInstance.delete(`/permissions/file`, {
         headers: { Authorization: accessToken },
         data: {
            grantedToEmail: userEmail,
            objectId: fileId,
         },
      });
      success("Permission Removed");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const ModifyBucketPermissions = async (accessToken, email, bucketId, role, error, success) => {
   try {
      await AxiosInstance.put(
         `/permissions/bucket/${bucketId}`,
         {
            grantedToEmail: email,
            access: role,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Permission Edited");
   } catch (err) {
      if (err.response.status === 400) {
         error(err.response.data.messages);
         console.log(err);
      } else {
         error(err.response.data.messages);
         console.log(err);
      }
   }
};

export const ModifyFolderPermissions = async (accessToken, email, folderId, role, error, success) => {
   try {
      await AxiosInstance.put(
         `/permissions/folder/${folderId}`,
         {
            grantedToEmail: email,
            access: role,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Permission Edited");
   } catch (err) {
      if (err.response.status === 400) {
         error(err.response.data.messages);
         console.log(err);
      } else {
         error(err.response.data.messages);
         console.log(err);
      }
   }
};

export const ModifyFilePermissions = async (accessToken, email, fileId, role, error, success) => {
   try {
      await AxiosInstance.put(
         `/permissions/file/${fileId}`,
         {
            grantedToEmail: email,
            access: role,
         },
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Permission Edited");
   } catch (err) {
      if (err.response.status === 400) {
         error(err.response.data.messages);
         console.log(err);
      } else {
         error(err.response.data.messages);
         console.log(err);
      }
   }
};

export const openFullscreen = () => {
   if (elem.requestFullscreen) {
      elem.requestFullscreen();
   } else if (elem.webkitRequestFullscreen) {
      elem.webkitRequestFullscreen();
   } else if (elem.msRequestFullscreen) {
      elem.msRequestFullscreen();
   }
};

export const alertTimeout = (delay, stateToChange) => {
   setTimeout(() => {
      stateToChange("");
   }, delay);
};

export const lambdaAuth = async (accessToken, error) => {
   try {
      const response = await AxiosInstance.get(`/connect/dropbox-auth-start`, {
         headers: { Authorization: accessToken },
      });
      window.open(response.data, "_blank", "noopener,noreferrer");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const getAllLambdas = async (accessToken, stateToChange, error) => {
   try {
      const response = await AxiosInstance.get(`/lambdas`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const activeLambdas = async (accessToken, stateToChange, bucketId, error) => {
   try {
      const response = await AxiosInstance.get(`/lambdas/active/bucket/${bucketId}`, {
         headers: { Authorization: accessToken },
      });
      stateToChange(response.data);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const lambdaFinish = async (accessToken, code, error, success, func) => {
   try {
      await AxiosInstance.get(`/connect/dropbox-auth-finish/?code=${code}`, {
         headers: { Authorization: accessToken },
      });
      success("Successfully Connected");
      setTimeout(() => {
         func();
      }, 1500);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err.response.data.messages);
   }
};

export const activateTheLambda = async (accessToken, bucketId, lambdaId, error, success) => {
   try {
      await AxiosInstance.put(
         `/lambdas/bucket/${bucketId}/activate?lambda=${lambdaId}`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Successfully Activated");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const deactivateTheLambda = async (accessToken, bucketId, lambdaId, error, success) => {
   try {
      await AxiosInstance.put(
         `/lambdas/bucket/${bucketId}/deactivate?lambda=${lambdaId}`,
         {},
         {
            headers: { Authorization: accessToken },
         }
      );
      success("Successfully Deactivated");
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};

export const getAllSpecificTags = async (accessToken, tagId, stateToChange, error) => {
   try {
      const response = await AxiosInstance.get(`/tags/${tagId}/objects`, {
         headers: { Authorization: accessToken },
      });
      console.log(response);
      stateToChange(response);
   } catch (err) {
      error(err.response.data.messages);
      console.log(err);
   }
};
