package br.com.willianantunes.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

@Data
@Component
@ConfigurationProperties(prefix = "custom", ignoreUnknownFields = false)
public class CustomProperties {

    private final CorsConfiguration cors = new CorsConfiguration();
}