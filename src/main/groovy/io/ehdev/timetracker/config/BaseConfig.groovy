package io.ehdev.timetracker.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackages = "io.ehdev.timetracker")
class BaseConfig {

    private static final Logger log = LoggerFactory.getLogger(BaseConfig.class)

    static {
        log.info("Scanning beans")
    }
}
