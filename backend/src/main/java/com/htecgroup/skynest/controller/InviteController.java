package com.htecgroup.skynest.controller;

import com.htecgroup.skynest.service.InviteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/invite")
@AllArgsConstructor
@Log4j2
@Tag(name = "Invite API", description = "Invite-related operations")
public class InviteController {

  private InviteService inviteService;

  @PostMapping("/{email}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority(T(com.htecgroup.skynest.model.entity.RoleEntity).ROLE_ADMIN)")
  public void sendRegistrationInvite(@PathVariable @NotNull @Email String email) {
    inviteService.sendRegistrationInvite(email);
  }
}
