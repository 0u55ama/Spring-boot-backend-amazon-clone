package com.amazon.amazon_clone.service;

import com.amazon.amazon_clone.dto.ResponseDto;
import com.amazon.amazon_clone.dto.user.SignInDto;
import com.amazon.amazon_clone.dto.user.SignInResponseDto;
import com.amazon.amazon_clone.exceptions.AuthenticationFailException;
import com.amazon.amazon_clone.service.AuthenticationService.*;
import com.amazon.amazon_clone.dto.user.SignupDto;
import com.amazon.amazon_clone.exceptions.CustomException;
import com.amazon.amazon_clone.model.AuthenticationToken;
import com.amazon.amazon_clone.model.User;
import com.amazon.amazon_clone.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignupDto signupDto) {
        //check if the user already present in the database
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new CustomException("USER ALREADY PRESENT");
        }
        //hash the password

        String encreptedPassword = signupDto.getPassword();
        try {
            encreptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        User user = new User(
                signupDto.getFirstName(),
                signupDto.getLastName(),
                signupDto.getEmail(),
                encreptedPassword);

        //save the user
        userRepository.save(user);

        //generate the auth token
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "USER CREATED SUCCESSFULLY");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        //find user by email

        User user = userRepository.findByEmail(signInDto.getEmail());
        if (Objects.isNull(user)){
            throw new AuthenticationFailException("USER IS NOT VALID");
        }

        //hash the password

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthenticationFailException("INCORRECT PASSWORD");

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //compare the password in DB
        // if we have a match

        AuthenticationToken token = authenticationService.getToken(user);
        //retrieve the token

        if (Objects.isNull(token)){
            throw new CustomException("TOKEN IS NOT PRESENT");
        }
        return new SignInResponseDto("success", token.getToken());
        //return response

    }
}
