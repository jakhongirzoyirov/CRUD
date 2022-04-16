package uz.pdp.springmvcjwtoauth2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springmvcjwtoauth2.entity.user.UserLoginDto;
import uz.pdp.springmvcjwtoauth2.response.ApiJwtResponse;
import uz.pdp.springmvcjwtoauth2.service.auth.AuthService;

import java.util.Map;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(
            value = "/login",
            consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE},
            produces = {APPLICATION_JSON_VALUE}
    )
    public ApiJwtResponse login(
            @RequestBody UserLoginDto userLoginDto
    ) {
        return authService.login(userLoginDto);
    }

    @PostMapping("/refresh/token")
    public String getAccessToken(
            @RequestBody Map<String, String> authorization
    ) {
        String refreshToken = authorization.get("refresh_token");
        return authService.getAccessToken(refreshToken);
    }


}
