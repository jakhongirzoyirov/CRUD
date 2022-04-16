package uz.pdp.springmvcjwtoauth2.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uz.pdp.springmvcjwtoauth2.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.exception.JwtExpiredTokenException;

import java.util.Date;

@Service
public class JwtProvider {

    @Value("${jwt.access.token.secret.key}")
    private String accessSecretKey;

    @Value("${jwt.refresh.token.secret.key}")
    private String refreshSecretKey;

    public String generateAccessToken(UserEntity userEntity) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, accessSecretKey)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60))
                .claim("authorities", userEntity.getPermissions())
                .compact();
    }

    public String generateRefreshToken(UserEntity userEntity) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, refreshSecretKey)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .claim("authorities", userEntity.getAuthorities())
                .compact();
    }

    public Claims parseAccessToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(accessSecretKey)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredTokenException("access token expired");
        } catch (Exception e) {
            return null;
        }
    }

    public String parseRefreshToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(refreshSecretKey)
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredTokenException("refresh token expired");
        } catch (Exception e) {
            return null;
        }
    }

}
