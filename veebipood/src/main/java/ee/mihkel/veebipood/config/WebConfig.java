package ee.mihkel.veebipood.config;

import ee.mihkel.veebipood.annotations.CurrentPersonArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CurrentPersonArgumentResolver currentPersonArgumentResolver;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // kõikidele API otspunktidele peale
                .allowedOrigins("http://localhost:5173")
                .allowedHeaders("GET", "POST", "PUT", "DELETE", "PATCH") // kõik headerid on lubatud
                .allowedMethods(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentPersonArgumentResolver);
//        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
