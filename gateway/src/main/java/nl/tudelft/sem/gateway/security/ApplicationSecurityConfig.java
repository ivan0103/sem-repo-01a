package nl.tudelft.sem.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    //    private final transient PasswordEncoder passwordEncoder;
    //
    //    @Autowired
    //    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
    //        this.passwordEncoder = passwordEncoder;
    //    }
    //
    //    @Override
    //    protected void configure(HttpSecurity http) throws Exception {
    //        http.csrf().disable()
    //            .authorizeRequests()
    //            .antMatchers(HttpMethod.POST, "http://localhost:8085/authentication/**").permitAll()
    //            .anyRequest()
    //            .authenticated()
    //            .and()
    //            .formLogin()
    //            .loginPage("/login").permitAll()
    //            .defaultSuccessUrl("/", true);
    //    }

    private final transient UserDetailsService userDetailsService;

    @Autowired
    public ApplicationSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }
}
