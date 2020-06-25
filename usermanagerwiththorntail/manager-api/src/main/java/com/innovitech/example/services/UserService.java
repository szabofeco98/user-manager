package com.innovitech.example.services;


import com.innovitech.example.domain.SearchUserQueryRequest;
import com.innovitech.example.domain.UserDTO;

import java.util.List;

public interface UserService {
    String login(UserDTO userDTO);

    List getAll(SearchUserQueryRequest request);

    String registration(UserDTO userDTO);

    String update(UserDTO userDTO);

    String delete(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

    long getUserCount();
}
