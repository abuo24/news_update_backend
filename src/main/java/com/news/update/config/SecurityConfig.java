package com.news.update.config;

import com.news.update.security.JwtAuthenticationEntryPoint;
import com.news.update.security.JwtConfigurer;
import com.news.update.security.JwtTokenFilter;
import com.news.update.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(@Lazy UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .antMatchers("/swagger-ui.html","/api/auth/**", "/api/admin/categories/all", "/api/admin/news/all",
                        "/api/admin/{categoryid}/shortnews","/api/admin/shortnews",
                        "/api/files/preview/{hashId}","/api/files/download/{hashId}",
                        "/api/comments/{newsid}","/api/comments/{newsid}/all").permitAll()
                .antMatchers("/api/admin/**","/api/files/**","/api/comments/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

        http.addFilterBefore(jwtAuthenticationEntryPoint(), UsernamePasswordAuthenticationFilter.class);
    }

    private Filter jwtAuthenticationEntryPoint() {
        return new JwtTokenFilter(jwtTokenProvider);
    }

}