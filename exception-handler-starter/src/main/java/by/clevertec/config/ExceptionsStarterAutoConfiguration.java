package by.clevertec.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import by.clevertec.advice.ErrorHandler;
import org.springframework.context.annotation.ComponentScan;

@AutoConfiguration
@EnableConfigurationProperties(ExceptionsStarterProperties.class)
@ConditionalOnProperty(prefix = "aop.exception.handler", name = "enabled", havingValue = "true")
@ComponentScan(basePackages = "by.clevertec.advice")
public class ExceptionsStarterAutoConfiguration {

    @Bean
    public ErrorHandler errorHandler() {
        return new ErrorHandler();
    }
}
