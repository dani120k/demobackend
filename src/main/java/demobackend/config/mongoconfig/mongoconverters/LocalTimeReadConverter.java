package demobackend.config.mongoconfig.mongoconverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

public class LocalTimeReadConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(String source) {
        return LocalTime.parse(source);
    }
}
