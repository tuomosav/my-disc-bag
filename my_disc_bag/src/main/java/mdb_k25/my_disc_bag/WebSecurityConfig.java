package mdb_k25.my_disc_bag;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private static final AntPathRequestMatcher[] WHITE_LIST_URLS = {
        new AntPathRequestMatcher("/api/discs**")};

        @Bean
        public SecurityFilterChain configure(HttpSecurity http) throws Exception {

            http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(antMatcher("/css/**")).permitAll()
                .requestMatchers(antMatcher("/register")).permitAll()
                .requestMatchers(antMatcher("/saveuser")).permitAll()
                .requestMatchers(WHITE_LIST_URLS).permitAll()
                .anyRequest().authenticated())
                .headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions
                    .disable()))
                .formLogin(formLogin ->
                    formLogin.loginPage("/login")
                    .defaultSuccessUrl("/disclist", true)
                    .permitAll())
                .logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable());

            return http.build();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        }
}