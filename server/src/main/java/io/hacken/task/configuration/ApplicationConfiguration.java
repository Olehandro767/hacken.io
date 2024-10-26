package io.hacken.task.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.*;

@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    public CorsConfigurationSource createCorsConfigurationSource(@Value("${server.trusted-urls}") String trustedUrls) {
        String[] urls = Arrays.stream(trustedUrls.split(",")).map(String::trim).toArray(String[]::new);
        String[] methods = {GET.name(), POST.name(), PUT.name(), OPTIONS.name()};
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(urls));
        configuration.setAllowedMethods(Arrays.asList(methods));
        configuration.setAllowedHeaders(List.of("*"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/static/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(new UrlResource(
                requireNonNull(this.getClass().getClassLoader().getResource("static"))));
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() {
        return authentication -> authentication;
    }

    @Bean
    public SecurityFilterChain configureSecurityFilterChain(@Value("${server.trusted-urls}") String trustedUrls,
                                                            HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(customizer ->
                        customizer.requestMatchers("/**").permitAll().anyRequest().hasRole("USER"))
                .cors(customizer -> customizer.configurationSource(this.createCorsConfigurationSource(trustedUrls)))
                .csrf(AbstractHttpConfigurer::disable).formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable).build();
    }
}
