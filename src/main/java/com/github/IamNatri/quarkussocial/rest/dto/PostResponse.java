package com.github.IamNatri.quarkussocial.rest.dto;

import com.github.IamNatri.quarkussocial.domain.model.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime createdAt;

    public static PostResponse fromEntity(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setText(post.getText());
        postResponse.setCreatedAt(post.getCreatedAt());
        return postResponse;
    }
}
