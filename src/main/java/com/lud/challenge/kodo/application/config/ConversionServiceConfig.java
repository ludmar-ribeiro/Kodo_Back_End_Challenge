package com.lud.challenge.kodo.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.util.Locale;

@Configuration
public class ConversionServiceConfig {

    {
        Locale.setDefault(Locale.US);
    }

    @Bean
    public ConversionService conversionService() {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();

        registrar.setUseIsoFormat(true);

        FormattingConversionService service = new DefaultFormattingConversionService();

        registrar.registerFormatters(service);

        return  service;
    }
}
