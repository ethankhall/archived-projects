package io.ehdev.easyinvoice.services

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.ehdev.easyinvoice.interfaces.ContactInfoWrapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Component
@RequestMapping("contact")
@Slf4j
class ContactService {

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public ContactInfoWrapper getContactInfo(@PathVariable String id){
        log.info("Getting contact for $id")
        ContactInfoWrapper.createSampleData()
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public def createCustomerInfo(@RequestBody ContactInfoWrapper contactInfoWrapper) {
        log.info("Should save: {}", JsonOutput.toJson(contactInfoWrapper))
        return [status: "created"]
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public def deleteContact(@PathVariable String id) {
        log.info("Should delete $id")
        [ status: "Deleted" ]
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public def updateContact(@PathVariable String id, @RequestBody ContactInfoWrapper contact) {
        log.info("Should update $id to be ${JsonOutput.toJson(contact)}")
        [ status: "Updated" ]
    }

}
