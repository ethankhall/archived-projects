package io.ehdev.timetracker.config
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("io.ehdev.timetracker")
@Slf4j
class BaseConfig {

    static {
        log.info("Scanning beans")
    }
}
