package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.lambda.LambdaType;
import com.htecgroup.skynest.service.BucketService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lambdas")
@AllArgsConstructor
@Log4j2
@Tag(name = "Lambda API", description = "Lambda operations")
public class LambdaController {

  private BucketService bucketService;

  @GetMapping
  public List<LambdaType> getAllLambdas() {
    return Arrays.stream(LambdaType.values()).collect(Collectors.toList());
  }

  @GetMapping("/active/bucket/{bucketId}")
  public List<LambdaType> getActiveLambdasForBucket(@PathVariable UUID bucketId) {
    List<LambdaType> activeLambdas = bucketService.getActiveLambdas(bucketId);
    log.info("Successfully got active lambdas for bucket {}", bucketId);
    return activeLambdas;
  }
}
