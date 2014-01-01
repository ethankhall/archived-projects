package io.ehdev.timetracker.services
import io.ehdev.timetracker.clock.Clock
import org.joda.time.format.ISODateTimeFormat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestEndpoint {

    @Autowired
    Clock clock

    @RequestMapping(method = RequestMethod.GET)
    public def getExampleData() {
        return [ createdAt: ISODateTimeFormat.dateTime().print(clock.getNow()), output: 'this is a test resource']
    }

}
