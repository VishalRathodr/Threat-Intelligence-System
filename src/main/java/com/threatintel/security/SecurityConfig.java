package com.threatintel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    public SecurityConfig(UserDetailsService userDetailsService,
                          LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    // ============================
    // ADMIN SECURITY
    // ============================

    @Bean
    @Order(1)
    public SecurityFilterChain adminSecurity(HttpSecurity http)
            throws Exception {

        http

            .securityMatcher("/admin/**")

            .authenticationProvider(authenticationProvider())

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(
                            "/admin/login",
                            "/css/**",
                            "/js/**",
                            "/images/**")
                    .permitAll()

                    .anyRequest()
                    .hasAuthority("ROLE_ADMIN"))

            .formLogin(form -> form

                    .loginPage("/admin/login")

                    .loginProcessingUrl("/admin/login")

                    .usernameParameter("username")

                    .passwordParameter("password")

                    .successHandler(loginSuccessHandler)

                    .failureUrl("/admin/login?error")

                    .permitAll())

            .logout(logout -> logout

                    .logoutUrl("/admin/logout")

                    .logoutSuccessUrl("/admin/login?logout")

                    .invalidateHttpSession(true)

                    .deleteCookies("JSESSIONID"));

        return http.build();
    }

    // ============================
    // USER SECURITY
    // ============================

    @Bean
    @Order(2)
    public SecurityFilterChain userSecurity(HttpSecurity http)
            throws Exception {

        http

            .authenticationProvider(authenticationProvider())

            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(

                            "/",

                            "/about",

                            "/services",

                            "/projects",

                            "/contact",

                            "/user/login",
                            
                            "/user/register",
                            

                            "/css/**",

                            "/js/**",

                            "/images/**")

                    .permitAll()

                    .requestMatchers("/user/**")

                    .hasAuthority("ROLE_USER")

                    .anyRequest()

                    .authenticated())

            .formLogin(form -> form

                    .loginPage("/user/login")

                    .loginProcessingUrl("/user/login")

                    .usernameParameter("username")

                    .passwordParameter("password")

                    .successHandler(loginSuccessHandler)

                    .failureUrl("/user/login?error")

                    .permitAll())

            .logout(logout -> logout

                    .logoutUrl("/logout")

                    .logoutSuccessUrl("/user/login?logout")

                    .invalidateHttpSession(true)

                    .deleteCookies("JSESSIONID"));

        return http.build();
    }
}