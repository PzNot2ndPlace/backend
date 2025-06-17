package ru.tbank.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Configuration
public class DateTimeConfig {

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService service = new DefaultConversionService();
        service.addConverter(new InstantToOffsetDateTimeConverter());
        return service;
    }

    public static class InstantToOffsetDateTimeConverter implements Converter<Instant, OffsetDateTime> {
        @Override
        public OffsetDateTime convert(Instant source) {
            return source != null ? OffsetDateTime.ofInstant(source, ZoneId.systemDefault()) : null;
        }
    }
}