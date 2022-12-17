package com.nanocode.sistemadereserva;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails usuario1 = User
                .withUsername("nahuelborromeo")
                .password("$2a$10$FXmqW.lKwYzws.dMZj7bGeK/2uT2kz.qK9tqZU9zgE89OIffLBFvi")
                .roles("USER")
                .build();

        UserDetails usuario2 = User
                .withUsername("admin")
                .password("$2a$10$FXmqW.lKwYzws.dMZj7bGeK/2uT2kz.qK9tqZU9zgE89OIffLBFvi")
                .roles("ADMIN")
                .build();

        UserDetails usuario3 = User
                .withUsername("nborromeo")
                .password("$2a$10$FXmqW.lKwYzws.dMZj7bGeK/2uT2kz.qK9tqZU9zgE89OIffLBFvi")
                .roles("DOCTOR")
                .build();

        return new InMemoryUserDetailsManager(usuario1,usuario2, usuario3);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/formPaciente/*", "/eliminar/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout().permitAll();
    }
}
