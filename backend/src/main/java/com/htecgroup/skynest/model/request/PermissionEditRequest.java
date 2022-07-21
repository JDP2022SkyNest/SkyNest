package com.htecgroup.skynest.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEditRequest {

  @NotNull(message = "user id cannot be null or empty")
  private UUID grantedTo;

  @Pattern(regexp = "(view)|(download)|(edit)|(owner)")
  private String access;
}
