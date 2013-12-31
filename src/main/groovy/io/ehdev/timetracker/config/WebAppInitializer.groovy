package io.ehdev.timetracker.config
import groovy.util.logging.Slf4j
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer

@Slf4j
class WebAppInitializer extends AbstractSecurityWebApplicationInitializer {

    WebAppInitializer() {
        super(WebConfig.class)
    }

//    @Override
//    void beforeSpringSecurityFilterChain(ServletContext container) {
//        // Create the 'root' Spring application context
//
//        container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
//                .addMappingForUrlPatterns(null, false, "/*");
//    }

//    void afterSpringSecurityFilterChain(ServletContext container){
//        // Register and map the dispatcher servlet
//        ServletRegistration.Dynamic dispatcher =
//                container.addServlet("DispatcherServlet", new DispatcherServlet(configurationClasses));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//    }

}
