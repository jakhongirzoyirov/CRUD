package uz.pdp.springmvcjwtoauth2.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserPermissionDto {
    private String name;
    private Set<String> permission;

    @JsonIgnore
    public Set<SimpleGrantedAuthority> getUserPermissions() {
        Set<SimpleGrantedAuthority> permissions = new HashSet<>();
        permissions.add(new SimpleGrantedAuthority("ROLE_" + name));

        if (permission != null)
            permissions.addAll(
                    permission
                            .stream()
                            .map((per) -> new SimpleGrantedAuthority(name + "::" + per))
                            .collect(Collectors.toSet()));
        return permissions;
    }

}
