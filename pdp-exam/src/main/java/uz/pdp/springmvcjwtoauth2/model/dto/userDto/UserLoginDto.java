package uz.pdp.springmvcjwtoauth2.model.dto.userDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private String username;
    private String password;
}
