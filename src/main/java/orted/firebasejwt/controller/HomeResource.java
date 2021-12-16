package orted.firebasejwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import orted.firebasejwt.entity.AuthenticateRequestBody;
import orted.firebasejwt.entity.CustomUserForContext;
import orted.firebasejwt.exception.UnauthorizedException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@Validated
public class HomeResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/home")
    public String home(){
        return "Welcome home you are authorized";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<CustomUserForContext> authenticate(@Valid @RequestBody AuthenticateRequestBody payload)
            throws UnauthorizedException {
        System.out.println(payload.toString());
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(payload.getPhone(), payload.getIdToken())
        );
        System.out.println(((CustomUserForContext)auth.getPrincipal()).toString());

        return ResponseEntity.ok().body((CustomUserForContext)auth.getPrincipal());
    }
}
