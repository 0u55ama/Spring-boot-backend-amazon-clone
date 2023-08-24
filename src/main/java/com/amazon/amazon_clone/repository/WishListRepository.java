package com.amazon.amazon_clone.repository;


import com.amazon.amazon_clone.model.User;
import com.amazon.amazon_clone.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {
    List<WishList> findAllByUserOrderByCreatedDateDesc(User user);

}
