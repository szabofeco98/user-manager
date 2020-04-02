package com.innovitech.example.database.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class Address implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String street;

    private Integer postalCode;

    private Integer houseNumber;

    private String city;

    @ManyToOne
    private User user;
}
