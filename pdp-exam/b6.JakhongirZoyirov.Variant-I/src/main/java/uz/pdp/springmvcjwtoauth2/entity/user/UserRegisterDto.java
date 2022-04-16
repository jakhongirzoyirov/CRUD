package uz.pdp.springmvcjwtoauth2.entity.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegisterDto {
    private String name;
    private String username;
    private String password;
    private List<UserPermissionDto> permissions;
}
