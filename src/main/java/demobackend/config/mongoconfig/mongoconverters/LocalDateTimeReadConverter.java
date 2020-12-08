package demobackend.config.mongoconfig.mongoconverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class LocalDateTimeReadConverter implements Converter<Date, LocalDateTime> {
    @Override
    public LocalDateTime convert(Date source) {
        LocalDateTime localDateTime = source.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime();
        return localDateTime;
    }
}
