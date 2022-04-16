package uz.pdp.springmvcjwtoauth2.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.service.auth.AuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null) {
//            throw new JwtException("Token not found or invalid");
            filterChain.doFilter(request, response);
            return;
        }

        if (!token.startsWith("Bearer "))
            throw new JwtException("Token is invalid");

        token = token.replace("Bearer ", "");
        Claims claims = this.parseToken(token);

        UserEntity userEntity = (UserEntity) authService.loadUserByUsername(claims.getSubject());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userEntity.getPassword(),
                null,
                userEntity.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    private Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey("tryToFind")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new ExpiredJwtException(null, null, "expired date");
        } catch (Exception e) {
            e.printStackTrace();
            throw new JwtException("secret key error");
        }
    }

}


