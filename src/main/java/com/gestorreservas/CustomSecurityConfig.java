package com.gestorreservas;

import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Configuration
//@EnableWebSecurity
public class CustomSecurityConfig {

    /*  @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                // ... endpoints
                .and()
                .formLogin()
                .defaultSuccessUrl("/index.xhtml");
        return http.build();
    }*/

}
