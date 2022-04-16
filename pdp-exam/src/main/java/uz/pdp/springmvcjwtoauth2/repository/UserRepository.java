package uz.pdp.springmvcjwtoauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameOrEmail(String username, String email);
    boolean existsByUsernameOrEmail(String username, String email);
}
