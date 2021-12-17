package orted.firebasejwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import orted.firebasejwt.entities.AuthenticateRequestBody;
import orted.firebasejwt.entities.CustomUserForContext;
import orted.firebasejwt.exceptions.UnauthorizedException;
import orted.firebasejwt.util.JwtUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
public class Authentication {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticateRequestBody payload)
            throws UnauthorizedException {
        System.out.println(payload.toString());

        //Authenticating user
        //This authentication manager will use custom authentication provider
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getPhone(), payload.getIdToken())
        );

        //got the custom data for user which in Context
        CustomUserForContext userInfo = ((CustomUserForContext) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        //sending the response
        return ResponseEntity.ok().body(jwtUtil.generateToken(userInfo));
    }
}
