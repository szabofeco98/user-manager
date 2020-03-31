package com.innovitech.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDTO {
    private Long id;

    private String username;

    private String password;

    private String name;

    private List<AddressDTO> addresses;
}
