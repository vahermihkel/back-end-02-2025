package ee.mihkel.veebipood.config;

import ee.mihkel.veebipood.security.JwtFilter;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/payment-status").permitAll()
                        .requestMatchers(HttpMethod.GET, "/supplier1").permitAll()
                        .requestMatchers(HttpMethod.GET, "/supplier2").permitAll()
                        .requestMatchers(HttpMethod.GET, "/supplier3").permitAll()
                        .requestMatchers(HttpMethod.GET, "/parcelmachines").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories").permitAll()
    //                    .requestMatchers(HttpMethod.DELETE, "/categories/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/persons").permitAll()

                        .requestMatchers(HttpMethod.POST, "/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/products").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/categories").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/categories").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/categories").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/categories").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/change-admin/*").hasAuthority("SUPERADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin-persons").hasAuthority("SUPERADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173")); // RestControllerist võin ära võtta @CrossOrigin
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
