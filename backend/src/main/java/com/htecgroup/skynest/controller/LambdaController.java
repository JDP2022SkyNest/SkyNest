package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.lambda.LambdaType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lambdas")
@AllArgsConstructor
@Tag(name = "Lambda API", description = "Lambda operations")
public class LambdaController {

  @GetMapping
  public List<LambdaType> getAllLambdas() {
    return Arrays.stream(LambdaType.values()).collect(Collectors.toList());
  }
}
