package com.amazon.amazon_clone.controller;


import com.amazon.amazon_clone.dto.checkout.CheckoutItemDto;
import com.amazon.amazon_clone.dto.checkout.StripeResponse;
import com.amazon.amazon_clone.service.AuthenticationService;
import com.amazon.amazon_clone.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderService orderService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto>checkoutItemDtoList)
    throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);

        StripeResponse stripeResponse = new StripeResponse(session.getId());

        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
    }
}
