package com.innovitech.example.services;


import com.innovitech.example.domain.AddressDTO;
import com.innovitech.example.domain.UserDTO;

import java.util.List;

public interface AddressService {
    List getAll();

    String persist(AddressDTO addressDTO);

    String update(AddressDTO addressDTO);

    String delete(AddressDTO addressDTO);

    List getAllByUser(Long id);
}
