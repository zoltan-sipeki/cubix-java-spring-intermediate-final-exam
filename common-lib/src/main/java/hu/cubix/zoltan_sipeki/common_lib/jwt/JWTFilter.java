package hu.cubix.zoltan_sipeki.common_lib.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER_NAME = "Authorization";

    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    private final JWTService jwtService;

    public JWTFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authHeaderValue = request.getHeader(AUTH_HEADER_NAME);
        if (authHeaderValue == null || !authHeaderValue.startsWith(AUTH_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeaderValue.substring(AUTH_HEADER_PREFIX.length());

        var userDetails = jwtService.parseToken(jwt);
        if (userDetails == null) {
            return;
        }

        var securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                UsernamePasswordAuthenticationToken.authenticated(userDetails, "", userDetails.getAuthorities()));
        SecurityContextHolder.setContext(securityContext);

        filterChain.doFilter(request, response);
    }
}
