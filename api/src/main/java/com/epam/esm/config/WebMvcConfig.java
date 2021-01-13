package com.epam.esm.config;


import com.epam.esm.dto.SearchCertificateDto;

import com.epam.esm.dto.search.SortByField;
import com.epam.esm.dto.search.SortOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    /**
     * Can't be replaced with lambda. Otherwise it fails.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, SortByField>() {
            @Override
            public SortByField convert(String source) {
                return SortByField.valueOf(source.toUpperCase());
            }
        });
        registry.addConverter(new Converter<String, SortOrder>() {
            @Override
            public SortOrder convert(String source) {
                return SortOrder.valueOf(source.toUpperCase());
            }
        });
    }
}