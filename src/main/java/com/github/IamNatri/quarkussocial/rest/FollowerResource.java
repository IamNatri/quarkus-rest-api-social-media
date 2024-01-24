package com.github.IamNatri.quarkussocial.rest;

import com.github.IamNatri.quarkussocial.domain.model.Follower;
import com.github.IamNatri.quarkussocial.domain.repository.FollowerRepository;
import com.github.IamNatri.quarkussocial.domain.repository.UserRepository;
import com.github.IamNatri.quarkussocial.rest.dto.FollowerRequest;
import com.github.IamNatri.quarkussocial.rest.dto.FollowerResponse;
import com.github.IamNatri.quarkussocial.rest.dto.FollowersPerUserResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Inject
    public FollowerResource(
            FollowerRepository followerRepository,
            UserRepository userRepository
    ) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response follow(
            @PathParam("userId") Long userId, FollowerRequest request
    ) {
        System.out.println("userId: " + userId + ", followerId: " + request.getFollowerId());

        if (userId.equals(request.getFollowerId())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        var user = userRepository.findById(userId);

        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = followerRepository.follows(follower, user);

        if(follows){
            return Response.status(Response.Status.CONFLICT).build();
        }

        var entity = new Follower();
        entity.setUser(user);
        entity.setFollower(follower);
        followerRepository.persist(entity);

        return Response.status(Response.Status.NO_CONTENT).build();



    }

    @GET
    public Response listFollowers(@PathParam("userId") Long userId){

        var user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var users = followerRepository.findByUser(userId);
        FollowersPerUserResponse responseObject = new FollowersPerUserResponse();
        responseObject.setFollowersCount(users.size());
        var followerList = users.stream().map(FollowerResponse::new)
                .collect(Collectors.toList());
        responseObject.setContent(followerList);
        return Response.ok(responseObject).build();
    }

    @DELETE
    @Transactional
    public Response unfollowUser(@PathParam("userId") Long user_id, @QueryParam("followerId") Long follower_id){
        var user = userRepository.findById(user_id);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(follower_id);
        if(follower == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean follows = followerRepository.follows(follower, user);
        if(!follows){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        followerRepository.deleteByFollowerAndUser(follower_id, user_id);

        return Response.noContent().build();
    }


}
