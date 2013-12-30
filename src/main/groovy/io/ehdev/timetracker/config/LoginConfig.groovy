package io.ehdev.timetracker.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
@EnableWebSecurity
class LoginConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) {
        http
            .authorizeRequests()
                .antMatchers("/**").hasRole("USER")
                .and()
            .openidLogin()
                .loginPage('/html/login.html')
                .permitAll()

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        auth
            .inMemoryAuthentication()
            .withUser("https://www.google.com/accounts/o8/id?id=lmkCn9xzPdsxVwG7pjYMuDgNNdASFmobNkcRPaWU")
            .password("password")
            .roles("USER");
    }
}
