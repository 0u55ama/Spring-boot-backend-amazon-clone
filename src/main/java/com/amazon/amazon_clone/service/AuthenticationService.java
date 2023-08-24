package com.amazon.amazon_clone.service;


import com.amazon.amazon_clone.exceptions.AuthenticationFailException;
import com.amazon.amazon_clone.model.AuthenticationToken;
import com.amazon.amazon_clone.model.User;
import com.amazon.amazon_clone.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);

    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token){
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if (Objects.isNull(authenticationToken)){
            return null;
        }
        //authenticationToken is not null
        return authenticationToken.getUser();
    }

    public void authenticate(String token){
        //null check
        if (!Objects.nonNull(token)){
            throw new AuthenticationFailException("TOKEN NOT PRESENT");
        }
        if (Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("TOKEN NOT VALID");
        }
    }
}
