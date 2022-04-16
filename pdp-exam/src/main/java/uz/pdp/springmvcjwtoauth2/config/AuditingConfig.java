package uz.pdp.springmvcjwtoauth2.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getCurrentAuditor")
public class AuditingConfig {

    @Bean
//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AuditorAware<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return () -> Optional.of(currentPrincipalName);
    }

}
