package uz.pdp.springmvcjwtoauth2.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    /**
     * [
     *  {
     *      "role": "ADMIN",
     *      "permissions": "[COURSE_ADD COURSE_LIST]"
     *  }
     * ]
     */

    private String permissions;

    private String email;

    @Enumerated()
    private Provider provider;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> userPermissions = new HashSet<>();
        try {
            UserPermissionDto[] userPermissionDtos = new ObjectMapper().readValue(permissions, UserPermissionDto[].class);
            for(UserPermissionDto userPermissionDto : userPermissionDtos)
                userPermissions.addAll(userPermissionDto.getUserPermissions());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userPermissions;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
