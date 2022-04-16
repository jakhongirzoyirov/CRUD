package uz.pdp.springmvcjwtoauth2.model.entity.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.springmvcjwtoauth2.model.entity.BaseEntity;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "book")
public class BookEntity extends BaseEntity<UserEntity> {

    @Column(nullable = false, unique = true)
    private String name;

}
