package com.htecgroup.skynest.annotation.validator;

import com.htecgroup.skynest.annotation.CurrentUserCanEditBucket;
import com.htecgroup.skynest.model.dto.LoggedUserDto;
import com.htecgroup.skynest.service.BucketService;
import com.htecgroup.skynest.service.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EditBucketValidator implements ConstraintValidator<CurrentUserCanEditBucket, UUID> {
  private final CurrentUserService currentUserService;
  private final BucketService bucketService;

  @Override
  public boolean isValid(UUID uuid, ConstraintValidatorContext context) {
    LoggedUserDto loggedUserDto = currentUserService.getLoggedUser();
    UUID loggedUserUuid = loggedUserDto.getUuid();

    UUID ownerId = bucketService.getBucket(uuid).getCreatedById();

    if (!loggedUserUuid.equals(ownerId)) {
      return false;
    }
    return true;
  }
}
