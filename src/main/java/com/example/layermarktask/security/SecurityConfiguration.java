package com.example.layermarktask.security;

import com.example.layermarktask.filters.CustomAuthenticationFilter;
import com.example.layermarktask.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.layermarktask.role.RoleTypes.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CustomAuthenticationEntrypoint customAuthenticationEntrypoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                        .exceptionHandling()
                                .authenticationEntryPoint(customAuthenticationEntrypoint).and();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.authorizeRequests()
                /* Common routes */
                .antMatchers(GET, "/api/v1/customers").permitAll()
                .antMatchers(GET, "/api/v1/librarians").permitAll()
                .antMatchers(GET, "/api/v1/libraries").permitAll()
                .antMatchers(GET, "/api/v1/books").permitAll()
                .antMatchers(GET, "/api/v1/books").permitAll()
                .antMatchers(GET, "/api/v1/reviews").permitAll()
                .antMatchers(GET, "/api/v1/customers/register").permitAll()
                .antMatchers(GET, "/api/v1/librarians/register").permitAll()
                .antMatchers("/v2/api-docs", "/v3/api-docs", "/swagger-resources/**", "/swagger-ui/**").permitAll()
                .antMatchers("/api/v1/populate").permitAll()

                /* Librarian routes */
                .antMatchers(PUT, "/api/v1/books/loan").hasAnyAuthority(ROLE_LIBRARIAN.name(), ROLE_ADMIN.name())
                .antMatchers(PUT, "/api/v1/books/return").hasAnyAuthority(ROLE_CUSTOMER.name(), ROLE_ADMIN.name())
                .antMatchers(GET, "/api/v1/reviews").hasAnyAuthority(ROLE_CUSTOMER.name(), ROLE_ADMIN.name())
                .antMatchers(POST, "/api/v1/reviews").hasAnyAuthority(ROLE_CUSTOMER.name(), ROLE_ADMIN.name())
                .antMatchers(GET, "/api/v1/books/{id}").hasAnyAuthority(ROLE_LIBRARIAN.name(), ROLE_CUSTOMER.name(), ROLE_ADMIN.name())
                .antMatchers(PUT, "/api/v1/libraries/assign-book").hasAnyAuthority(ROLE_LIBRARIAN.name(), ROLE_ADMIN.name())

                /* Admin routes */
                .antMatchers(GET, "/api/**").hasAnyAuthority(ROLE_ADMIN.name())
                .antMatchers(POST, "/api/**").hasAnyAuthority(ROLE_ADMIN.name())
                .antMatchers(PUT, "/api/**").hasAnyAuthority(ROLE_ADMIN.name())
                .antMatchers(POST, "/api/v1/customers/add-role").hasAnyAuthority(ROLE_ADMIN.name())
                .antMatchers(POST, "/api/v1/librarians/add-role").hasAnyAuthority(ROLE_ADMIN.name())
                .anyRequest().authenticated();

        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
