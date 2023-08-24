package com.amazon.amazon_clone.controller;


import com.amazon.amazon_clone.common.ApiResponse;
import com.amazon.amazon_clone.dto.ProductDto;
import com.amazon.amazon_clone.model.Product;
import com.amazon.amazon_clone.model.User;
import com.amazon.amazon_clone.model.WishList;
import com.amazon.amazon_clone.service.AuthenticationService;
import com.amazon.amazon_clone.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    AuthenticationService authenticationService;

    //Post/Save a product
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,
                                                     @RequestParam("token") String token){
        //auth the token
        authenticationService.authenticate(token);

        //find user
        User user = authenticationService.getUser(token);

        //save the item in the wishlist
        WishList wishList = new WishList(user, product);
        wishListService.createWishList(wishList);

        ApiResponse apiResponse = new ApiResponse(true, "ADDED TO THE WISHLIST");

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    //Get/retrieve all products
    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {

        //auth the token
        authenticationService.authenticate(token);

        //find user
        User user = authenticationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);

    }













    //Todo Delete nd Update
}
