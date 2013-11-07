package io.ehdev.easyinvoice.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebMvc
@ComponentScan([ "io.ehdev.easyinvoice" ])
@EnableAsync
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/")
        registry.addResourceHandler("/img/**").addResourceLocations("/img/")
        registry.addResourceHandler("/js/**").addResourceLocations("/js/")
        registry.addResourceHandler("/html/**").addResourceLocations("/html/")
    }

}
