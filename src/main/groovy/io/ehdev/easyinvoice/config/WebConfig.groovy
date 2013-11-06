package io.ehdev.easyinvoice.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableWebMvc
@ComponentScan([ "io.ehdev.easyinvoice" ])
@EnableAsync
@EnableScheduling
public class WebConfig {

}
