package orted.firebasejwt.exceptions;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    This is when we define access on the basis of roles
    so when that role user is not given access for particular thing
    Server sends forbidden 403, in this way we can customise response for that

    401 --> unauthorized (when credentials are invalid like id_token/jwt/username/password are invalid )

    403 --> forbidden (credentials are valid but lack some information to give you access
            for asked thing)
 */
public class CustomUnauthorizedInFilter implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException, ServletException {
        res.setContentType("application/json");
        res.setStatus(401);
        res.getWriter().write("Unauthorized Invalid token");
    }
}
