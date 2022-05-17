package com.htecgroup.SkyNest.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = true)
    private String userID;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String surname;

    @Column(nullable = true)
    private String encryptedPassword;

    //  TODO

    //private UserType type;

    //private String company;

}
