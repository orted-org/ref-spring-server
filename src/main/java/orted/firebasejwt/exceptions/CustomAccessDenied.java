package orted.firebasejwt.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        res.setContentType("application/json");
        res.setStatus(403);
        res.getWriter().write("Access Denied... Forbidden because of role");
    }
}
