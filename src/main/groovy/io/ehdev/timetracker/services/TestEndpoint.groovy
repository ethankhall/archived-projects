package io.ehdev.timetracker.services

import org.joda.time.DateTime
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class TestEndpoint {

    @RequestMapping(method = RequestMethod.GET)
    public def getExampleData() {
        return [ createdAt: DateTime.now(), output: 'this is a test resource']
    }

}
