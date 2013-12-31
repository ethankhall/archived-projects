package io.ehdev.timetracker.services
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = '/checkauth')
@Slf4j
class OpenIdEndpoint {

    @RequestMapping(method = RequestMethod.GET)
    public void redirectToLoginPage(@RequestParam Map openIdAuth){
        log.debug(new JsonBuilder(openIdAuth).toPrettyString())
    }
}
