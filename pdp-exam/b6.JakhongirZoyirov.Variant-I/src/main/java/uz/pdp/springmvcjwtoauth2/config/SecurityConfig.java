package uz.pdp.springmvcjwtoauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.springmvcjwtoauth2.filter.JwtFilterToken;
import uz.pdp.springmvcjwtoauth2.service.auth.AuthService;
import uz.pdp.springmvcjwtoauth2.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtFilterToken jwtFilterToken;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterBefore(jwtFilterToken, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .antMatchers("/admin/**").hasRole("SUPER_ADMIN")
                .antMatchers(HttpMethod.GET, "/login").permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .oauth2Login()
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(
//                            HttpServletRequest request,
//                            HttpServletResponse response,
//                            Authentication authentication
//                    ) throws IOException, ServletException {
//                        DefaultOidcUser auth = (DefaultOidcUser) authentication.getPrincipal();
//                        String token = userService.authentication(auth);
//                        response.setHeader("Authorization", "Bearer " + token);
//                        response.sendRedirect("/home");
//                    }
//                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder);
    }
}
