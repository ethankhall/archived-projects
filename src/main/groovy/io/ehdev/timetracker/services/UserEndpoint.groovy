package io.ehdev.timetracker.services
import groovy.util.logging.Slf4j
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/api/user')
@Slf4j
class UserEndpoint {

    @RequestMapping(method=RequestMethod.GET)
    public String show(Model model, OpenIDAuthenticationToken authentication) {
        model.addAttribute("authentication", authentication);
        return "user/show";
    }
}
