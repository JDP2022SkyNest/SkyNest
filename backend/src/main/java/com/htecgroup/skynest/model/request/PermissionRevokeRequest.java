package com.htecgroup.skynest.model.request;

import com.htecgroup.skynest.util.RegexUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionRevokeRequest {
    @NotNull(message = "cannot be null")
    @Pattern(message = "format is not valid", regexp = RegexUtil.EMAIL_FORMAT_REGEX)
    @Size(max = 254, message = "length cannot be over 254 characters")
    private String grantedToEmail;

    @NotNull(message = "object id cannot be null or empty")
    private UUID objectId;
}
