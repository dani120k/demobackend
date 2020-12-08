package demobackend.config.mongoconfig.mongoconverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class LocalDateTimeWriteConverter implements Converter<LocalDateTime, Date> {
    @Override
    public Date convert(LocalDateTime source) {
        Date date = new Date(source.atZone(ZoneOffset.UTC).toInstant().toEpochMilli());
        return date;
    }
}
