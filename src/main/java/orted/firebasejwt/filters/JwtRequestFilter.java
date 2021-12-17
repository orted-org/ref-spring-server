package orted.firebasejwt.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import orted.firebasejwt.entities.CustomUserForContext;
import orted.firebasejwt.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }


        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                //validating jwt will throw error if jwt is invalid
                CustomUserForContext userInfo = jwtUtil.validateToken(jwt);
                System.out.println(userInfo.toString());

                //hardcoding the authorities for testing
                List<GrantedAuthority> authorities
                        = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("user"));

                //creating authentication object
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userInfo, null, authorities);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                /*
                    putting Authentication object in context is necessary
                    otherwise application will give unauthorized error
                 */
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (Exception e) {
                //invalid jwt
                System.out.println(e.getMessage());
            }
        }
        //forwarding to the next channel
        chain.doFilter(request, response);
    }

}
