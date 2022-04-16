package uz.pdp.springmvcjwtoauth2.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.model.dto.userDto.UserLoginDto;
import uz.pdp.springmvcjwtoauth2.repository.UserRepository;
import uz.pdp.springmvcjwtoauth2.response.ApiJwtResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameOrEmail(username, username).
                orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public ApiJwtResponse login(
            UserLoginDto userLoginDto
    ) {
        Optional<UserEntity> optionalUserEntity =
                userRepository.findByUsernameOrEmail(userLoginDto.getUsername(), userLoginDto.getUsername());

        if (optionalUserEntity.isEmpty())
            throw new UsernameNotFoundException("username or email not found");

        UserEntity userEntity = optionalUserEntity.get();

        if (!(passwordEncoder.matches(userLoginDto.getPassword(), userEntity.getPassword())))
            throw new UsernameNotFoundException("username or email not found");

        String accessToken = jwtProvider.generateAccessToken(optionalUserEntity.get());
        String refreshToken = jwtProvider.generateRefreshToken(optionalUserEntity.get());

        ApiJwtResponse apiJwtResponse = new ApiJwtResponse();
        apiJwtResponse.setAccessToken(accessToken);
        apiJwtResponse.setRefreshToken(refreshToken);
        apiJwtResponse.setStatusCode(0);

        return apiJwtResponse;

    }

    public String getAccessToken(String refreshToken) {
        String username = jwtProvider.parseRefreshToken(refreshToken);
        Optional<UserEntity> optionalUserEntity = userRepository.findByUsernameOrEmail(username, username);

        if(optionalUserEntity.isEmpty())
            throw new UsernameNotFoundException(username + " not found");

        return jwtProvider.generateAccessToken(optionalUserEntity.get());

    }

}
