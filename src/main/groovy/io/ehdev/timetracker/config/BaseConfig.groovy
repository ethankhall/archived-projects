package io.ehdev.timetracker.config
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@ComponentScan("io.ehdev.timetracker")
@Slf4j
@Import([HibernateConfig.class, LoginConfig.class, PropertyFileLoader.class, WebConfig.class])
class BaseConfig {

    static {
        log.info("Scanning beans")
    }
}
