package fr.utc.sr03.chatapp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is used to configure the Jackson library
 * It is used to disable some features of the Jackson library
 * This is done to ensure that the Jackson library does not fail on unknown
 * properties
 * and to ensure that the dates are not written as timestamps
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .featuresToDisable(
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        DeserializationFeature.ACCEPT_FLOAT_AS_INT,
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}
