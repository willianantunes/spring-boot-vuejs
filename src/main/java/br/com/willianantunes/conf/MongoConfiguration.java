package br.com.willianantunes.conf;

import br.com.willianantunes.domain.util.JSR310DateConverters.DateToZonedDateTimeConverter;
import br.com.willianantunes.domain.util.JSR310DateConverters.ZonedDateTimeToDateConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {

        DefaultDbRefResolver resolver = new DefaultDbRefResolver(mongoDbFactory);

        MappingMongoConverter converter = new MappingMongoConverter(resolver, context);
        // In order to remove _class field
        // Example of how it is without this configuration: "_class": "br.com.editoraglobo.domain.Brand"
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;
    }

    @Bean
    public MongoCustomConversions customConversions() {

        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(DateToZonedDateTimeConverter.INSTANCE);
        converters.add(ZonedDateTimeToDateConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }

    /**
     * @see <a href="https://stackoverflow.com/a/22583492/3899136">How to I get Spring-Data-MongoDB to validate my objects?</a>
     */
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(Validator validator) {

        return new ValidatingMongoEventListener(validator);
    }
}