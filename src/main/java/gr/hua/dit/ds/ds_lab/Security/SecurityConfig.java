package gr.hua.dit.ds.ds_lab.Security;

import gr.hua.dit.ds.ds_lab.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private UserService userService;

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/users/register", "/users/api/register", "/users/login", "/users/api/login", "/css/**", "/js/**").permitAll() // Επιτρέπουμε την πρόσβαση
                        .anyRequest().authenticated() // Προστατεύουμε τις υπόλοιπες σελίδες
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Διαχείριση session
                )
                .formLogin(login -> login
                        .loginPage("/users/login") // Η σελίδα σύνδεσης χρήστη
                        .defaultSuccessUrl("/users", true) // Όταν συνδεθεί ο χρήστης πάει εδώ
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/users/login") // Όταν γίνει αποσύνδεση, ο χρήστης πηγαίνει στην σελίδα σύνδεσης
                        .invalidateHttpSession(true) // Τερματίζουμε το session, κατά την αποσύνδεση
                        .deleteCookies("JSESSIONID") // Σβήνουμε το cookie, κατά την αποσύνεση
                        .permitAll()
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }

}
