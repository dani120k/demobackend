package demobackend.config.mongoconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import demobackend.config.mongoconfig.mongoconverters.*;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoCustomConverterConfigs {

    @Value("${spring.data.mongodb.uri}")
    public String mongodbConnectionString;

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        List list = new ArrayList<>();
        list.add(new LocalDateReadConverter());
        list.add(new LocalDateWriteConverter());
        list.add(new LocalDateTimeReadConverter());
        list.add(new LocalDateTimeWriteConverter());
        list.add(new BigDecimalReadConverter());
        list.add(new BigDecimalWriteConverter());
        list.add(new LocalTimeReadConverter());
        list.add(new LocalTimeWriteConverter());
        return new MongoCustomConversions(list);
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoClientDbFactory(mongodbConnectionString);
    }

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        MongoMappingContext mappingContext = new MongoMappingContext();
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
        mongoConverter.setCustomConversions(mongoCustomConversions());

        return mongoConverter;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(), mongoConverter());
    }
}