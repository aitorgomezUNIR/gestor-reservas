package com.gestorreservas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Aitor Gómez Afonso
 */
@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityHandler securityHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/_**/**").denyAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/javax.faces.resource/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/css/**").permitAll()
                //.antMatchers("/resources/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/prelogin")
                .loginPage("/login.xhtml")
                .permitAll()
                .failureUrl("/login.xhtml?error=true")
                .successHandler(securityHandler)
                .and()
                .logout()
                .permitAll()
                .logoutUrl("/user/log/out")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login.xhtml?logout=true")
                .invalidateHttpSession(true);
        http.sessionManagement().maximumSessions(1);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

}
