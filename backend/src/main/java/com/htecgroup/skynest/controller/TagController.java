package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.model.request.TagCreateRequest;
import com.htecgroup.skynest.model.response.TagResponse;
import com.htecgroup.skynest.service.TagService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
@Tag(name = "Tag API", description = "Tag-related operations")
public class TagController {

  private TagService tagService;

  @PostMapping
  public ResponseEntity<TagResponse> createTag(
      @Valid @RequestBody TagCreateRequest tagCreateRequest) {

    ResponseEntity<TagResponse> tagResponseEntity =
        new ResponseEntity<>(tagService.createTag(tagCreateRequest), HttpStatus.OK);

    return tagResponseEntity;
  }
}
