package com.github.IamNatri.quarkussocial.rest;

import com.github.IamNatri.quarkussocial.domain.model.User;
import com.github.IamNatri.quarkussocial.domain.repository.UserRepository;
import com.github.IamNatri.quarkussocial.rest.dto.CreateUserRequest;
import com.github.IamNatri.quarkussocial.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.Set;

@Path("/users")
@Consumes("application/json") // what type of data we expect to receive, can pass this way
@Produces(MediaType.APPLICATION_JSON) // what type of data we will return (in this case JSON) or this
public class UserResource {

    private final UserRepository repository;
    private final Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @POST
    @Transactional
    public Response createUser(CreateUserRequest userRequest) {

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);
        if (!violations.isEmpty()) {
            return  ResponseError
                    .fromConstraintViolations(violations)
                    .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setUsername(userRequest.getUserName());
        user.setAge(userRequest.getAge());


        repository.persist(user);


        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(user)
                .build();
    }

    @GET
    public Response listallUsers(){
        PanacheQuery<User> users = repository.findAll();
        return Response.ok(users.list()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id){
        User user = repository.findById(id);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
        }
        repository.delete(user);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userRequest){
        User user = repository.findById(id);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).build();
        }
        user.setUsername(userRequest.getUserName());
        user.setAge(userRequest.getAge());
        return Response.noContent().build();
    }
}
