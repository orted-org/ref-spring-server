package orted.firebasejwt.services;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import orted.firebasejwt.entities.CustomUserForContext;
import orted.firebasejwt.exceptions.UnauthorizedException;

import java.util.ArrayList;

/*
    This is my custom firebase authentication provider and
    Which will authenticate(will check the integrity of id token)

    on successful
    1. will check weather user exists with this phone number, if not then it will create
    2. will put the complete information about the user that we want to be accessed throughout
        the application and to put it into jwt, in security context
 */
@Service
public class FirebaseAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO :- extract the Details and validate them
        String phoneNumber = (String) authentication.getPrincipal();
        String id_token = (String) authentication.getCredentials();
        System.out.println("My custom provider ran " + phoneNumber + id_token);

        // TODO :- validate id token with firebase admin sdk
        // TODO :- check and complete user info in db
        // TODO :- put the relevant data in context/or we can directly return from here and handle there
        // TODO :- incase of invalid id_token throw an Error of UnauthorizedException type


        CustomUserForContext user = new CustomUserForContext("123", "govind", "myBusiness");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, id_token, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
