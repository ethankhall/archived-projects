package io.ehdev.timetracker.config
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
class PropertyFileLoader {

    private static final Logger log = LoggerFactory.getLogger(PropertyFileLoader.class)

    @Configuration
    @PropertySource("classpath:test.properties")
    @Profile("test")
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
