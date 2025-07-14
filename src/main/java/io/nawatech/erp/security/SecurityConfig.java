package io.nawatech.erp.security;


import io.nawatech.erp.security.permission.CustomPermissionEvaluator;
import io.nawatech.erp.security.permission.PermissionService;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.filter.RequestContextFilter;

@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
@Slf4j
public class SecurityConfig {

    @Autowired
    private AuditLoginSuccessHandler auditLoginSuccessHandler;

    @Bean
    public OidcUserService oidcUserService() {
        return new CustomOidcUserService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
            throws Exception {

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler(PermissionEvaluator permissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }

    @Bean
    public PermissionEvaluator permissionEvaluator(PermissionService permissionService) {
        return new CustomPermissionEvaluator(permissionService);
    }

    private static final String[] STATIC_RESOURCES = {
            "/assets/**",
            "/vendors/**",
            "/favicon.ico",
            "/css/**",
            "/js/**",
            "/images/**",
            "/webjars/**"
    };

    private static final String[] WHITE_LIST_URL = {
            "/login/**",
            "/register/**",
            "/index",
            "/2fa",
            "/api/rate-limited",
            "/verifyEmail/**",
            "/password-reset/**",
            "/resend-verification-token",
            "/forgot-password/**",
            "/forgot-password-token",
            "/token/verify"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        log.info("SecurityFilterChain");
        http
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(STATIC_RESOURCES).permitAll()
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers("/admin/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("error", exception.getMessage());
                            response.sendRedirect("/login?error");
                        })
                        .successHandler(auditLoginSuccessHandler)
                        .usernameParameter("email")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout.logoutSuccessUrl("/login").permitAll());

        return http.build();
    }
}