package demobackend.config.mongoconfig.mongoconverters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class LocalDateWriteConverter implements Converter<LocalDate, Date> {
    @Override
    public Date convert(LocalDate source) {
        Date date = new Date(source.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli());
        return date;
    }
}
