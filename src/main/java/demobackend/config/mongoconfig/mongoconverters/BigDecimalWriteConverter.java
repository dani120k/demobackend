package demobackend.config.mongoconfig.mongoconverters;

import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalWriteConverter implements Converter<BigDecimal, Decimal128> {
    @Override
    public Decimal128 convert(BigDecimal bigDecimal) {
        return new Decimal128(bigDecimal.setScale(20, RoundingMode.HALF_EVEN));
    }
}
