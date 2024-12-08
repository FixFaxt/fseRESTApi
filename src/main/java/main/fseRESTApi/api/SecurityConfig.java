package main.fseRESTApi.api;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import main.fseRESTApi.api.model.User;
import main.fseRESTApi.api.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
        .httpBasic(basic -> basic.disable());

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService(UserService userService) {
    return email -> {
      Optional<User> user = userService.findByEmail(email);
      if (user.isEmpty()) {
        throw new UsernameNotFoundException("User not found");
      }

      User existingUser = user.get();
      return new org.springframework.security.core.userdetails.User(
          existingUser.getEmail(),
          existingUser.getPassword(),
          List.of(new SimpleGrantedAuthority("ROLE_" + existingUser.getRole().name())));
    };
  }
}
