package ch.heigvd.amt.project.two.auth.api.filters;

import ch.heigvd.amt.project.two.auth.configuration.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token.isEmpty()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "You must add \"Authorization\" header");
            return;
        }

        String emailFromToken = JwtTokenUtil.getEmailFromToken(token);

        if (emailFromToken.isEmpty()) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Your token isn't valid");
            return;
        }

        // Authentication successful
        request.setAttribute("email", emailFromToken);
        request.setAttribute("isBlocked", JwtTokenUtil.getIsBlockedFromToken(token));
        request.setAttribute("isAdmin", JwtTokenUtil.getIsAdminFromToken(token));

        filterChain.doFilter(request, response);

    }
}
