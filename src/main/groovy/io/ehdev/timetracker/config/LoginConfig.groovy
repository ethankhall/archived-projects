package io.ehdev.timetracker.config

import io.ehdev.timetracker.security.SecuredUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl

@Configuration
@EnableWebSecurity
class LoginConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .openidLogin()
                .loginPage("/login.jsp")
                .permitAll()
                .authenticationUserDetailsService(new SecuredUserDetailsService())
                .attributeExchange("https://www.google.com/.*")
                    .attribute("email")
                        .type("http://axschema.org/contact/email")
                        .required(true)
                        .and()
                    .attribute("firstname")
                        .type("http://axschema.org/namePerson/first")
                        .required(true)
                        .and()
                    .attribute("lastname")
                        .type("http://axschema.org/namePerson/last")
                        .required(true)
                        .and()
                    .and()
                .attributeExchange(".*yahoo.com.*")
                    .attribute("email")
                        .type("http://axschema.org/contact/email")
                        .required(true)
                        .and()
                    .attribute("fullname")
                        .type("http://axschema.org/namePerson")
                        .required(true)
                        .and()
                    .and()
                .attributeExchange(".*myopenid.com.*")
                    .attribute("email")
                        .type("http://schema.openid.net/contact/email")
                        .required(true)
                        .and()
                    .attribute("fullname")
                    .type("http://schema.openid.net/namePerson")
                    .required(true);
    }


    @Bean(name = 'tokenRepo')
    InMemoryTokenRepositoryImpl getTokenRep() {
        return new InMemoryTokenRepositoryImpl();
    }
}
