package uz.pdp.springmvcjwtoauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.springmvcjwtoauth2.model.entity.user.UserEntity;
import uz.pdp.springmvcjwtoauth2.repository.UserRepository;

@SpringBootApplication
public class SpringMvcJwtOauth2Application implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcJwtOauth2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserEntity superAdmin = new UserEntity();

        superAdmin.setName("Arman");
        superAdmin.setUsername("smile");
        superAdmin.setPassword(passwordEncoder.encode("181"));
        superAdmin.setPermissions("[{\"name\": \"SUPER_ADMIN\"}]");

        userRepository.save(superAdmin);

    }
}
