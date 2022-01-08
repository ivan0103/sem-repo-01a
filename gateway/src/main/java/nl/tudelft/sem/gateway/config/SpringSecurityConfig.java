//package nl.tudelft.sem.gateway.config;
//
//import nl.tudelft.sem.gateway.services.GatewayService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.
//annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.
// annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.
// web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.client.RestTemplate;
//
//
//@Configuration
//@EnableWebSecurity
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final transient RestTemplate restTemplate;
//
//    @Autowired
//    public SpringSecurityConfig(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new GatewayService(restTemplate);
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        //super.configure(httpSecurity);
//        httpSecurity//.authorizeRequests()
//                //.antMatchers("/").permitAll().and()
//                .authorizeRequests()
//                .antMatchers("/h2-console/**").permitAll().and()
//                .authorizeRequests()
//                .antMatchers("/authentication/create/**").permitAll()
//                .anyRequest().authenticated();
//
//        httpSecurity.csrf().disable();
//        httpSecurity.headers().frameOptions().disable();
//        httpSecurity.formLogin().and()
//                .logout().invalidateHttpSession(true)
//                .clearAuthentication(true).permitAll();
//    }
//}
