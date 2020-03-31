package com.innovitech.example.database.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String name;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL})
    private List<Address> addresses;
}
