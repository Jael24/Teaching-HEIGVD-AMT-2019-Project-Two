package ch.heigvd.amt.project.two.auth.api.filters;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class IsBlockedFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getMethod().equals("POST")) {
            Boolean isUserBlocked = (Boolean) request.getAttribute("isBlocked");

            if (isUserBlocked) {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "You are blocked");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
