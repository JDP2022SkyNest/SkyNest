package com.htecgroup.SkyNest.enitity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 8310242347168695452L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String userID;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String encryptedPassword;

    //  TODO

    //private UserType type;

    //private String company;

}
