package br.com.willianantunes.conf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class SeriaDeserConfiguration {

    @Bean
    Jackson2ObjectMapperBuilder jacksonBuilder() {

        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }

    @Bean
    JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    /**
     * Module for serialization/deserialization of RFC 7807.
     *
     * @see <a href="https://zalando.github.io/problem">Know more about Problem</a>
     */
    @Bean
    ProblemModule problemModule() {
        return new ProblemModule();
    }

    @Bean
    ConstraintViolationProblemModule constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}