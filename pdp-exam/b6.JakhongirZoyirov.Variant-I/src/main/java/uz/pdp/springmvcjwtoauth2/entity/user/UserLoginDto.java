package uz.pdp.springmvcjwtoauth2.entity.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private String username;
    private String password;
}
