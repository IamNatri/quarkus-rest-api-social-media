package com.github.IamNatri.quarkussocial.domain.repository;

import com.github.IamNatri.quarkussocial.domain.model.Follower;
import com.github.IamNatri.quarkussocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower>{
    public boolean follows(User follower, User user){
        var params = Parameters.with("follower", follower)
                .and("user", user).map();

        PanacheQuery<Follower> query = find("follower = :follower and user = :user", params);

        Optional<Follower> followerOptional = query.firstResultOptional();

        return followerOptional.isPresent();
    }

    public List<Follower> findByUser(Long userId){
        return find("user.id", userId).list();
    }

    public void deleteByFollowerAndUser(Long followerId, Long userId) {
        var params = Parameters
                .with("userId", userId)
                .and("followerId", followerId)
                .map();

        delete("follower.id =:followerId and user.id =: userId", params);
    }
}
