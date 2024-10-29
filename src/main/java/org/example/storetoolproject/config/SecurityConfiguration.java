package org.example.storetoolproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET, "/api/store/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/store/save-product").hasRole("SENIOR-USER")
                        .requestMatchers(HttpMethod.PUT, "/api/store/update-product").hasRole("SENIOR-USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/store/delete-product").hasRole("ADMIN")
                ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin-pass")
                .roles("ADMIN", "SENIOR-USER", "JUNIOR-USER")
                .build();

        UserDetails seniorOfficer = User.withDefaultPasswordEncoder()
                .username("senior-user")
                .password("senior-pass")
                .roles("SENIOR-USER")
                .build();

        UserDetails juniorOfficer = User.withDefaultPasswordEncoder()
                .username("junior-user")
                .password("junior-pass")
                .roles("JUNIOR-USER")
                .build();

        return new InMemoryUserDetailsManager(admin, seniorOfficer, juniorOfficer);
    }
}

