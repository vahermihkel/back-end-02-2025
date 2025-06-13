package ee.mihkel.veebipood.security;

// @Bean --> teen funktsioone beaniks
//klasside beaniks tegemine: @RestController, @Service, @Component

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.replace("Bearer ", "");

            Claims claims = jwtUtil.validateToken(token);
            String userId = claims.getSubject();
            log.info("User id {}", userId);
            String role = claims.get("role").toString();
            log.info("User role {}", role);

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (!role.equals("USER")) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                authorities.add(authority);
            }

            if (role.equals("SUPERADMIN")) {
                GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
                authorities.add(adminAuthority);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);
    }
}
