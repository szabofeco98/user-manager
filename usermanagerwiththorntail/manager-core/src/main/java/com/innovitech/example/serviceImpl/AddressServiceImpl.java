package com.innovitech.example.serviceImpl;


import com.innovitech.example.database.entity.Address;
import com.innovitech.example.database.repository.AddressDAO;
import com.innovitech.example.domain.AddressDTO;
import com.innovitech.example.services.AddressService;
import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Singleton
@Default
public class AddressServiceImpl implements AddressService {

    private ModelMapper modelMapper = new ModelMapper();

    @Inject
    private AddressDAO addressDAO;

    @Override
    public String update(AddressDTO addressDTO) {
        Address address = modelMapper.map(addressDTO, Address.class);
        try {
            addressDAO.update(address);
            return "success";
        }
        catch (PersistenceException e){
            return "fail";
        }
    }

}
