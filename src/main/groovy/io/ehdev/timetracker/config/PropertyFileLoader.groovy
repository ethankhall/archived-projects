package io.ehdev.timetracker.config
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
@Slf4j
class PropertyFileLoader {

    @Configuration
    @PropertySource("classpath:test.properties")
    @Profile("test")
    @Slf4j
    static class Defaults
    {
        static {
            log.info("Creating profile: test")
        }

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
