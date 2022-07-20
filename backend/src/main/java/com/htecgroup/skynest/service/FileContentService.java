package com.htecgroup.skynest.service;

import org.springframework.core.io.InputStreamResource;

import java.io.InputStream;

public interface FileContentService {

  InputStreamResource findById(String objectId);

  String save(String name, String type, InputStream fileContentStream);

  void deleteById(String objectId);
}
