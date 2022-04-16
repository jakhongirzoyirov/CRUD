package uz.pdp.springmvcjwtoauth2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.exception.JwtExpiredTokenException;
import uz.pdp.springmvcjwtoauth2.response.ApiExceptionResponse;
import uz.pdp.springmvcjwtoauth2.service.auth.JwtProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilterToken extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String authorization = request.getHeader("Authorization");

            if(authorization == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = authorization.replace("Bearer ", "");

            Claims claims = jwtProvider.parseAccessToken(accessToken);

            UserEntity userEntity = new UserEntity();
            userEntity.setPermissions((String) claims.get("authorities"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(),
                    null,
                    userEntity.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtExpiredTokenException ex) {
            setErrorResponse(response, ex.getMessage());
        }

    }

    private void setErrorResponse(
            HttpServletResponse response,
            String message) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        ApiExceptionResponse apiError
                = new ApiExceptionResponse(1000, message);
        try {
            response.getWriter().write(new ObjectMapper().writeValueAsString(apiError));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
