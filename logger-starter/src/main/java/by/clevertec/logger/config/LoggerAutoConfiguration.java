package by.clevertec.logger.config;

import by.clevertec.logger.aop.LoggerAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(LoggerProperties.class)
@ConditionalOnProperty(prefix = "aop.logging", name = "enabled", havingValue = "true")
public class LoggerAutoConfiguration {

    @Bean
    public LoggerAspect loggerAspect() {
        return new LoggerAspect();
    }
}
