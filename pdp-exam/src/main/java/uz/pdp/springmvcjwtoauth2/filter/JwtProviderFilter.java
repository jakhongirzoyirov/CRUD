package uz.pdp.springmvcjwtoauth2.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.model.dto.userDto.UserLoginDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtProviderFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            UserLoginDto userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userLoginDto.getUsername(),
                    userLoginDto.getPassword()
            );
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        UserEntity userEntity = (UserEntity) authResult.getPrincipal();
        String accessToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "accessSecretKey")
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 5))
                .claim("authorities", userEntity.getAuthorities())
                .compact();

        String refreshToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "refreshSecretKey")
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60))
                .claim("authorities", userEntity.getAuthorities())
                .compact();

        response.setHeader("access-token", accessToken);
        response.setHeader("refresh-token", refreshToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
