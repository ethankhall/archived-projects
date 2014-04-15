package io.ehdev.easyinvoice.config

import io.ehdev.easyinvoice.accessor.ContactInfoAccessor
import io.ehdev.easyinvoice.accessor.ContactInfoInMemoryAccessor
import io.ehdev.easyinvoice.accessor.InvoiceAccessor
import io.ehdev.easyinvoice.accessor.InvoiceInMemoryAccessor
import io.ehdev.easyinvoice.accessor.LineItemAccessor
import io.ehdev.easyinvoice.accessor.LineItemInMemoryAccessor
import org.springframework.context.annotation.Bean
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

    @Bean
    public LineItemAccessor getLineItemAccessor(){
        return new LineItemInMemoryAccessor();
    }

    @Bean
    public ContactInfoAccessor getContactInfoAccessor() {
        return new ContactInfoInMemoryAccessor();
    }

    @Bean
    public InvoiceAccessor getInvoiceAccessor() {
        return new InvoiceInMemoryAccessor();
    }

}
