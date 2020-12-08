package demobackend.config.mongoconfig.mongoconverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public class LocalDateReadConverter implements Converter<Date, LocalDate> {
    @Override
    public LocalDate convert(Date source) {
        LocalDate localDateTime = source.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        return localDateTime;
    }
}
