package nl.tudelft.sem.gateway.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import nl.tudelft.sem.gateway.filters.CustomAuthenticationFilter;
import nl.tudelft.sem.gateway.filters.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    public SpringSecurityConfig(
            UserDetailsService userDetailsService,
            BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    /**
     * Configures the security of the application with customized filters for JWT tokens
     * and permission restrictions based on role of user.
     *
     * @param http - the http we are configuring
     * @throws Exception When a user is not authorized or authenticated.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/users/addUser/**", "/refreshToken/**").permitAll();
        http.authorizeRequests().antMatchers("/student/**").hasAnyAuthority("Student");
        http.authorizeRequests().antMatchers("/company/**").hasAnyAuthority("Company");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(
                new CustomAuthorizationFilter(),
                UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * Configure security so that passwords are encoded.
     *
     * @param auth - the authentication manager builder.
     * @throws Exception When something wrong with the userDetails.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
