package io.ehdev.timetracker.config
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebMvc
@EnableAsync
@EnableScheduling
@Import(BaseConfig.class)
@ComponentScan(basePackages = "io.ehdev.timetracker")
class WebConfig extends WebMvcConfigurerAdapter {
}
