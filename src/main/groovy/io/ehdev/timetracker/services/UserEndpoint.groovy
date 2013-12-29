package io.ehdev.timetracker.services

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import io.ehdev.timetracker.services.external.user.ExternalUser
import io.ehdev.timetracker.storage.user.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/user')
@Slf4j
class UserEndpoint {

    @Autowired UserDao userDao

    @RequestMapping(method = RequestMethod.POST)
    public def createUser(@RequestBody ExternalUser user){
        log.info("Creating new user with options:\n {}", new JsonBuilder(user).toPrettyString())
        userDao.save(user.convertToUser())
        return [ status: 'OK']
    }

    @RequestMapping(value = '/example', method = RequestMethod.GET)
    public ExternalUser getExampleUser(){
        new ExternalUser(email: "some_email@domain.com", name: 'John Doe')
    }
}
