package io.ehdev.timetracker.services
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.security.SecureUserDetailsService
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.user.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/api/user')
@Slf4j
class UserEndpoint {

    @Autowired
    UserDao userDao

    @Autowired
    SecureUserDetailsService userDetailsService

    @RequestMapping(method=RequestMethod.GET)
    public def show(OpenIDAuthenticationToken authentication) {
        ExternalUser.convertUser(userDetailsService.getUser(authentication))
    }
}
