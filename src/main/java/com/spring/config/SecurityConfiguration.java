package com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((auth)->
                auth.requestMatchers("/football/*","/basketball/*",
                        "/swimming/*","/subs/*").authenticated()
                        .requestMatchers("/about/","/connect/").permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        
        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource) throws Exception{
        return new JdbcUserDetailsManager(dataSource);
    }*/

    /*@Bean
    public UserDetailsService userDetailsService() throws Exception{
        var admin = new InMemoryUserDetailsManager();
        admin.createUser(
                User.withUsername("anakin")
                        .password(encoder().encode("anakin"))
                        .authorities("ACCESS_ALL","ROLE_ADMIN")
                        .build()
        );

        admin.createUser(
                User.withUsername("anakin1")
                        .password(encoder().encode("anakin1"))
                        .authorities("ACCESS_ALL","ROLE_ADMIN")
                        .build()
        );

        return admin;
    }
*/
    @Bean
    public NoOpPasswordEncoder encoder(){
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}
