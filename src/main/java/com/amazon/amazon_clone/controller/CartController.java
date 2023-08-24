package com.amazon.amazon_clone.controller;

import com.amazon.amazon_clone.common.ApiResponse;
import com.amazon.amazon_clone.dto.ProductDto;
import com.amazon.amazon_clone.dto.cart.AddToCartDto;
import com.amazon.amazon_clone.dto.cart.CartDto;
import com.amazon.amazon_clone.model.Product;
import com.amazon.amazon_clone.model.User;
import com.amazon.amazon_clone.service.AuthenticationService;
import com.amazon.amazon_clone.service.CartService;
import com.amazon.amazon_clone.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    public CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;



    //post cart api
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token){

        //auth the token
        authenticationService.authenticate(token);

        //find user
        User user = authenticationService.getUser(token);

        cartService.addToCart(addToCartDto, user);

        return new ResponseEntity<>(new ApiResponse(true, "PRODUCT HAS BEEN ADDED TO CART"), HttpStatus.CREATED);
    }


    //get all cart item for a user
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token){

        //auth the token
        authenticationService.authenticate(token);

        //find user
        User user = authenticationService.getUser(token);

        //get cart items
        CartDto cartDto = cartService.listCartItems(user);
        return  new ResponseEntity<>(cartDto, HttpStatus.OK);


    }

    //delete a cart item for a user
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
                                                      @RequestParam("token") String token){

        //auth the token
        authenticationService.authenticate(token);

        //find user
        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(itemId, user);
        return new ResponseEntity<>(new ApiResponse(true, "PRODUCT HAS BEEN DELETED FROM CART"), HttpStatus.OK);


    }

}
