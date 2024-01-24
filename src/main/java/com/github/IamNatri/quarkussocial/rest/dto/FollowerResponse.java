package com.github.IamNatri.quarkussocial.rest.dto;

import com.github.IamNatri.quarkussocial.domain.model.Follower;
import lombok.Data;

@Data
public class FollowerResponse {
    private Long id;
    private String name;

    public FollowerResponse() {
    }
    public FollowerResponse(Follower follower) {
        this(follower.getId(), follower.getFollower().getUsername());
    }


    public FollowerResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
