package com.amazon.amazon_clone.repository;


import com.amazon.amazon_clone.model.AuthenticationToken;
import com.amazon.amazon_clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);

}
