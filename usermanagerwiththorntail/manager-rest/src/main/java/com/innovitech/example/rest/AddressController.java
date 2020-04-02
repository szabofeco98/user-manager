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

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public String add(AddressDTO addressDTO) {
        return addressService.persist(addressDTO);
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(AddressDTO addressDTO) {
        return addressService.update(addressDTO);
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAll() {
        return addressService.getAll();
    }

    @GET
    @Path("/getByUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List getByUser(@PathParam("userId") Long id) {
        return addressService.getAllByUser(id);
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(AddressDTO addressDTO) {
        return addressService.delete(addressDTO);
    }

}
