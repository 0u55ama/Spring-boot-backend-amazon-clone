package com.amazon.amazon_clone.controller;


import com.amazon.amazon_clone.dto.ResponseDto;
import com.amazon.amazon_clone.dto.user.SignInDto;
import com.amazon.amazon_clone.dto.user.SignInResponseDto;
import com.amazon.amazon_clone.dto.user.SignupDto;
import com.amazon.amazon_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("user")
@RestController
public class UserController {
    //sing up and sign in apis

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto){
        return userService.signIn(signInDto);
    }
}
