package com.htecgroup.SkyNest.model.enitity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 8310242347168695452L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank private String userId;
  @NotBlank private String email;
  @NotBlank private String name;
  @NotBlank private String surname;
  @NotBlank private String encryptedPassword;

  //  TODO
  //  private Boolean deleted = false;

  // private UserType type;

  // private String company;

}
