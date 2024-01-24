package com.github.IamNatri.quarkussocial.domain.repository;

import com.github.IamNatri.quarkussocial.domain.model.Post;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Post> {
}
