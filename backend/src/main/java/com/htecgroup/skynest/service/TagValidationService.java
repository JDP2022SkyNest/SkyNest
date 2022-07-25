package com.htecgroup.skynest.service;

import com.htecgroup.skynest.model.entity.ObjectToTagEntity;
import com.htecgroup.skynest.model.request.TagCreateRequest;

public interface TagValidationService {

    void checkIfTagAlreadyExists(TagCreateRequest tagCreateRequest);

    void checkIfTagOnObjectAlreadyExists(ObjectToTagEntity objectToTagEntity);

    void checkIfObjectIsTagged(ObjectToTagEntity objectToTagEntity);

    void checkIfTagAndObjectHasTheSameCompany(ObjectToTagEntity objectToTagEntity);

    void checkIfObjectIsNotDeleted(ObjectToTagEntity objectToTagEntity);
}
