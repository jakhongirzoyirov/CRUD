package uz.pdp.springmvcjwtoauth2.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.springmvcjwtoauth2.model.entity.BaseEntity;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto extends BaseEntity<UserEntity> {
    private String name;
}
