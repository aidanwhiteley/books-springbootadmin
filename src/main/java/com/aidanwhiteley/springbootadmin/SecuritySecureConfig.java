package com.aidanwhiteley.springbootadmin;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {

    private static final String DEFAULT_USERNAME = "testuser";
    private static final String DEFAULT_PASSWORD = "testpass";

    private final String adminContextPath;

    @Value("${spring.security.user.name}")
    private String springSecurityUserName;

    @Value("${spring.security.user.password}")
    private String springSecurityUserPassword;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecuritySecureConfig.class);

    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Check for default user name password usage
        if (springSecurityUserName.equalsIgnoreCase(DEFAULT_USERNAME) || springSecurityUserPassword.equalsIgnoreCase(DEFAULT_PASSWORD)) {
            LOGGER.warn("");
            LOGGER.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            LOGGER.warn("You are using the default username/password for this Spring Boot Admin project.");
            LOGGER.warn("If you are not running in development, you really don't want to be doing this. ");
            LOGGER.warn("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            LOGGER.warn("");
        }

        // @formatter:off
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");

        http.authorizeRequests()
            .antMatchers(adminContextPath + "/assets/**").permitAll()
            .antMatchers(adminContextPath + "/login").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
        .logout().logoutUrl(adminContextPath + "/logout").and()
        .httpBasic().and()
        .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers(
                adminContextPath + "/instances",
                adminContextPath + "/actuator/**"
            );
        // @formatter:on
    }
}
