package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.entity.FileMetadataEntity;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.FileRepository;
import com.htecgroup.skynest.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "File API", description = "Operations related to files")
@Log4j2
public class FileController {

  public final FileRepository fileRepository;
  public final UserRepository userRepository;
  public final BucketRepository bucketRepository;

  @GetMapping
  public ResponseEntity<List<String>> getAllFiles() {
    Iterable<FileMetadataEntity> all = fileRepository.findAll();
    List<String> files = new ArrayList<>();
    all.forEach(file -> files.add(file.toString()));
    return ResponseEntity.ok(files);
  }

  @PostMapping
  public ResponseEntity<String> createFile() {
    FileMetadataEntity fileEntity = new FileMetadataEntity();
    fileEntity.setCreatedBy(userRepository.findAll().get(0));
    fileEntity.setName("test file");
    fileEntity.setBucket(bucketRepository.findAll().iterator().next());
    fileEntity.setSize(100);
    fileEntity.setType("application/txt");
    return ResponseEntity.ok(fileRepository.save(fileEntity).toString());
  }

  @DeleteMapping
  public ResponseEntity<String> deleteFile() {
    FileMetadataEntity fileEntity = fileRepository.findAll().iterator().next();
    fileRepository.delete(fileEntity);
    return ResponseEntity.ok("deleted " + fileEntity);
  }
}
