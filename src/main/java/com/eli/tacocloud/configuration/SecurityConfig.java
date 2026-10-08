package com.eli.tacocloud.configuration;

import com.eli.tacocloud.model.TacoUser;
import com.eli.tacocloud.repository.UserRepository;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(new User(
                "buzz", passwordEncoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_USER")))
        );

        userDetailsList.add(new User(
                "woody", passwordEncoder.encode("password2"), List.of(new SimpleGrantedAuthority("ROLE_USER"))
        ));

        return new InMemoryUserDetailsManager(userDetailsList);
    } */

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            TacoUser user = userRepository.findByUsername(username);

            if (user != null){
                return user;
            }

            throw new UsernameNotFoundException("User " + username + "not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity){
        httpSecurity.authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/design", "/orders").hasRole("USER")
                                .requestMatchers("/", "/**").permitAll()

                );

        httpSecurity.formLogin((formLogin) ->{
            formLogin.loginPage("/login")
                    .defaultSuccessUrl("/design");
        });

        httpSecurity.oauth2Login((login) ->{
           login.loginPage("/login");
        });

        httpSecurity.logout((logout) ->{
            logout.logoutSuccessUrl("/register");
        });

        httpSecurity
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return httpSecurity.build();
    }
}
