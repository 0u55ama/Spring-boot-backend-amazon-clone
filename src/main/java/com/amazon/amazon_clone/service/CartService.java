package com.amazon.amazon_clone.service;

import com.amazon.amazon_clone.dto.cart.AddToCartDto;
import com.amazon.amazon_clone.dto.cart.CartDto;
import com.amazon.amazon_clone.dto.cart.CartItemDto;
import com.amazon.amazon_clone.exceptions.CustomException;
import com.amazon.amazon_clone.model.Cart;
import com.amazon.amazon_clone.model.Product;
import com.amazon.amazon_clone.model.User;
import com.amazon.amazon_clone.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;


    public void addToCart(AddToCartDto addToCartDto, User user) {

        //validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());

        //save the cart
        cartRepository.save(cart);

    }

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;

        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);

        return cartDto;


    }

    public void deleteCartItem(Integer cartItemId, User user) {

        //check item id if it blog to the user requesting the operation
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()){
            throw new CustomException("CART ITEM ID IS INVALID: " + cartItemId);
        }
        Cart cart = optionalCart.get();
        if (cart.getUser() != user){
            throw new CustomException("CART ITEM DOES NOT BELONG TO USER :" + cartItemId);
        }

        cartRepository.delete(cart);
    }
}
