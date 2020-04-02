package com.innovitech.example.rest;

import com.innovitech.example.domain.UserDTO;
import com.innovitech.example.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserController {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String response(UserDTO user) {
        System.out.println(user);
        System.out.println(userService.login(user));
        return userService.login(user);
    }

    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    public String registration(UserDTO user) {
        System.out.println(user);
        return userService.registration(user);
    }

    @GET
    @Path("/getUserByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDTO getUserByUsername(@PathParam("username") String username) {
        return userService.getUserByUsername(username);
    }

    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public String update(UserDTO userDTO){
        return userService.update(userDTO);
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List getAll() {
        return userService.getAll();
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(UserDTO userDTO) {
        return userService.delete(userDTO);
    }
}
