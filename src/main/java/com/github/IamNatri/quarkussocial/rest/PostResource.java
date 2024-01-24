package com.github.IamNatri.quarkussocial.rest;

import com.github.IamNatri.quarkussocial.domain.model.Post;
import com.github.IamNatri.quarkussocial.domain.model.User;
import com.github.IamNatri.quarkussocial.domain.repository.FollowerRepository;
import com.github.IamNatri.quarkussocial.domain.repository.PostRepository;
import com.github.IamNatri.quarkussocial.domain.repository.UserRepository;
import com.github.IamNatri.quarkussocial.rest.dto.CreatePostRequest;
import com.github.IamNatri.quarkussocial.rest.dto.PostResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowerRepository followerRepository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository postRepository, FollowerRepository followerRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.followerRepository = followerRepository;
    }

    @POST
    @Transactional
    public Response createPost(@PathParam("userId") Long userId, CreatePostRequest request) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        postRepository.persist(post);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response getPosts(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(followerId == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User follower = userRepository.findById(followerId);

        if(follower == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        boolean follows = followerRepository.follows(follower, user);
        if (!follows) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        PanacheQuery<Post> query = postRepository
                .find("user", Sort.by("createdAt", Sort.Direction.Descending), user);

        var list = query.list();

        var postResponseList = list.stream()
                .map(PostResponse::fromEntity)
                .collect(Collectors.toList());



        return Response.ok(postResponseList).build();
    }

}
