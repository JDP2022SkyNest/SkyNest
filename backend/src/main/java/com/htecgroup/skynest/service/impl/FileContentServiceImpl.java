package com.htecgroup.skynest.service.impl;

import com.htecgroup.skynest.exception.file.FileIOException;
import com.htecgroup.skynest.exception.file.FileNotFoundException;
import com.htecgroup.skynest.service.FileContentService;
import com.mongodb.MongoException;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileContentServiceImpl implements FileContentService {

  private final GridFsOperations operations;

  public InputStreamResource findById(String objectId) {
    try {
      if (StringUtils.isAllBlank(objectId)) throw new FileNotFoundException();

      GridFSFile gridFSFile = operations.findOne(new Query(Criteria.where("_id").is(objectId)));
      if (gridFSFile == null) throw new FileNotFoundException();

      InputStream inputStream = operations.getResource(gridFSFile).getInputStream();
      return new InputStreamResource(inputStream);
    } catch (IOException | MongoException e) {
      log.error(e);
      throw new FileIOException();
    }
  }

  public String save(String name, String type, InputStream fileContentStream) {
    try {
      Object objectId = operations.store(fileContentStream, name, type);
      return objectId.toString();
    } catch (MongoException e) {
      log.error(e);
      throw new FileIOException();
    }
  }

  public void deleteById(String objectId) {
    try {
      if (StringUtils.isAllBlank(objectId)) throw new FileNotFoundException();
      operations.delete(new Query(Criteria.where("_id").is(objectId)));
    } catch (MongoException e) {
      log.error(e);
      throw new FileIOException();
    }
  }
}
