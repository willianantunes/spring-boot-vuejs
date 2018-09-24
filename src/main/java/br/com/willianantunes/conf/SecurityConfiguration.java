package br.com.willianantunes.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    /**
     * It's important to mention that it is wide open because it's a test project.
     * A correct approach maybe will be done in the future.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // To avoid 401
        http.authorizeRequests().anyRequest().permitAll();
        // To avoid 403
        http.csrf().ignoringAntMatchers("/**").disable();
    }
}