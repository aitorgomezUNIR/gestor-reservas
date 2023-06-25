package com.gestorreservas.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Component
public class SecurityHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String url = "search.xhtml";
        response.sendRedirect(url.startsWith("/") ? url.replaceFirst("/", "") : url);
    }
}
