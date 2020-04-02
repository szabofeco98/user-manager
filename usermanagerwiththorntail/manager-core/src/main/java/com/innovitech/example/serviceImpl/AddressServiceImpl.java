package com.innovitech.example.serviceImpl;


import com.innovitech.example.database.entity.Address;
import com.innovitech.example.database.repository.AddressDAO;
import com.innovitech.example.domain.AddressDTO;
import com.innovitech.example.services.AddressService;
import com.innovitech.example.validator.Validator;
import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Default
public class AddressServiceImpl implements AddressService {

    private ModelMapper modelMapper = new ModelMapper();

    @Inject
    private AddressDAO addressDAO;

    @Override
    public List getAll() {
        return addressDAO.findAll();
    }

    @Override
    public String persist(AddressDTO addressDTO) {
        if (!Validator.requiredValidator(addressDTO.getStreet(), addressDTO.getPostalCode(),
                addressDTO.getHouseNumber(), addressDTO.getCity())) {
            return "every field required";
        }

        try {
            Address address = modelMapper.map(addressDTO, Address.class);
            addressDAO.persist(address);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String update(AddressDTO addressDTO) {
        if (!Validator.requiredValidator(addressDTO.getStreet(), addressDTO.getPostalCode(),
                addressDTO.getHouseNumber(), addressDTO.getCity())) {
            return "every field required";
        }

        Address address = modelMapper.map(addressDTO, Address.class);
        try {
            addressDAO.update(address);
            return "success";
        } catch (Exception e) {
            return Validator.exceptionHandler(e);
        }
    }

    @Override
    public String delete(AddressDTO addressDTO) {
        Address address = addressDAO.find(addressDTO.getId());
        addressDAO.remove(address);
        return "ok";
    }

    @Override
    public List getAllByUser(Long id) {
        return addressDAO.findByUser(id);
    }

}
