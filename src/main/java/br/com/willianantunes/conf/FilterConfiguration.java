package br.com.willianantunes.conf;

import br.com.willianantunes.controllers.components.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class FilterConfiguration {

    @Autowired
    private CustomProperties customProperties;

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = customProperties.getCors();

        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {

            source.registerCorsConfiguration(UrlBuilder.REQUEST_PATH_API + "/**", config);
        }

        return new CorsFilter(source);
    }
}