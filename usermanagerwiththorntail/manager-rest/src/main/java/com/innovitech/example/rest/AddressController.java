package com.innovitech.example.rest;

import com.innovitech.example.domain.AddressDTO;
import com.innovitech.example.services.AddressService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/address")
public class AddressController {


    @Inject
    private AddressService addressService;

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(AddressDTO addressDTO){
        return addressService.update(addressDTO);
    }

}
