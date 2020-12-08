package demobackend.config.mongoconfig.mongoconverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeWriteConverter implements Converter<LocalTime, String> {
    @Override
    public String convert(LocalTime source) {
        return source.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
