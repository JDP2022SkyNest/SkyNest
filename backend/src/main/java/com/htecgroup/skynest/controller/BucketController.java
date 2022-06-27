package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.entity.BucketEntity;
import com.htecgroup.skynest.repository.BucketRepository;
import com.htecgroup.skynest.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buckets")
@RequiredArgsConstructor
@Tag(name = "Bucket API", description = "Operations related to buckets")
@Log4j2
public class BucketController {

  private final BucketRepository bucketRepository;
  private final UserRepository userRepository;

  @GetMapping
  public ResponseEntity<List<String>> getAllBuckets() {
    Iterable<BucketEntity> all = bucketRepository.findAll();
    List<String> buckets = new ArrayList<>();
    all.forEach(bucket -> buckets.add(bucket.toString()));
    return ResponseEntity.ok(buckets);
  }

  @PostMapping
  public ResponseEntity<String> createBucket() {
    BucketEntity bucketEntity = new BucketEntity();
    bucketEntity.setName("test bucket");
    bucketEntity.setDescription("desc");
    bucketEntity.setCreatedBy(userRepository.findAll().get(0));
    bucketEntity.setIsPublic(true);
    return ResponseEntity.ok(bucketRepository.save(bucketEntity).toString());
  }
}
