package com.innovitech.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDTO {
    private Long id;

    private String street;

    private Integer postalCode;

    private Integer houseNumber;

    private String city;

    private UserDTO user;
}
