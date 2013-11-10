package io.ehdev.easyinvoice.services
import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.ehdev.easyinvoice.interfaces.InvoiceWrapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

@Component
@RequestMapping("invoice")
@Slf4j
public class InvoiceService {

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseBody
    public InvoiceWrapper getInvoice(@PathVariable String id, HttpServletRequest request){
        log.info("Requesting invoice {}", id)
        def connectionString = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/frontend"
        InvoiceWrapper.createSampleInvoice(connectionString)
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public def createNewPost(@RequestBody InvoiceWrapper invoice){
        log.info("'Saving' data")
        log.info(JsonOutput.toJson(invoice))
        return [ 'status' : 'accepted', id: invoice.id]
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public def deleteInvoice(@PathVariable String id){
        log.info("Deleting {}", id)
        return [ 'status' : 'deleted' ]
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public def updateInvoice(@PathVariable String id, @RequestBody InvoiceWrapper invoice){
        log.info("Updating {}", id)
        log.info(JsonOutput.toJson(invoice))
        return [ 'status' : 'updating' ]
    }

}
