package uz.pdp.springmvcjwtoauth2.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import uz.pdp.springmvcjwtoauth2.model.entity.user.Provider;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.repository.UserRepository;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public String authentication(DefaultOidcUser defaultOidcUser) {
        Map<String, Object> userClaims = defaultOidcUser.getClaims();
        String email = (String) userClaims.get("email");
        boolean existsUser = existsByUsernameOrEmail(email);

        if(!existsUser) {
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail(email);
            userEntity.setName((String) userClaims.get("name"));
            userEntity.setProvider(Provider.GOOGLE);
            userEntity.setPermissions("[{\"name\": \"USER\"");
            userRepository.save(userEntity);
        }

        return generateToken(email);

    }

    public boolean existsByUsernameOrEmail(String username) {
        return userRepository.existsByUsernameOrEmail(username, username);
    }

    private String generateToken(String username) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, "secretKeyGo")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 60))
                .setSubject(username)
                .compact();
    }
}
