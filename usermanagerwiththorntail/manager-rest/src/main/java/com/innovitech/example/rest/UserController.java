package com.innovitech.example.rest;

import com.innovitech.example.domain.SearchUserQueryRequest;
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
    public String login(UserDTO user) {
        return userService.login(user);
    }

    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    public String registration(UserDTO user) {
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
    public List getAll(
            @QueryParam("limit") int limit,
            @QueryParam("page") int page,
            @QueryParam("sortableElem") String sortableElem,
            @QueryParam("sortType") String sortType
            ) {
        SearchUserQueryRequest searchUserQueryRequest = SearchUserQueryRequest.builder()
                .limit(limit).page(page).sortableElem(sortableElem).sortType(sortType).build();
        System.out.println(searchUserQueryRequest);
        return userService.getAll(searchUserQueryRequest);
    }

    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(UserDTO userDTO) {
        return userService.delete(userDTO);
    }

    @GET
    @Path("/getUserCount")
    @Produces(MediaType.APPLICATION_JSON)
    public long getUserCount(){
        return userService.getUserCount();
    }
}
