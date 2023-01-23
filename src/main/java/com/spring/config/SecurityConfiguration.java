package com.spring.config;

import com.spring.constants.SecurityConstant;
import com.spring.filters.FilterBefore;
import com.spring.filters.JWTTokenFilter;
import com.spring.filters.JwtVaidation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((auth)->
                auth.requestMatchers("/football/*"
                                ,"/subs/*").hasRole("ADMIN")
                        .requestMatchers("/swimming/*").hasAnyRole("MANGER","ADMIN")
                        .requestMatchers("/basketball/*").hasRole("USER")
                        .requestMatchers("/login").authenticated()
                        .requestMatchers("/about/","/connect/").permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        var config = new CorsConfiguration();
                        // angular website
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(2500L);
                        config.setExposedHeaders(Arrays.asList(SecurityConstant.HEADER));
                        return config;
                    }
                }).and()
                .addFilterAfter(new JWTTokenFilter(),BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtVaidation(), BasicAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


                //.csrf().ignoringRequestMatchers("/basketball/*")
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        return http.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(Da0taSource dataSource) throws Exception{
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
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
