package io.ehdev.easyinvoice.config.rest

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.ehdev.easyinvoice.config.rest.invoice.CreateResponse
import io.ehdev.easyinvoice.invoice.Invoice
import io.ehdev.easyinvoice.invoice.InvoiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*

@Component
@RequestMapping("invoice")
@Slf4j
public class InvoiceConfig {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Invoice getInvoice(){
        return new InvoiceImpl();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public CreateResponse createNewPost(@RequestBody InvoiceImpl request){
        log.info("'Saving' data")
        log.info(JsonOutput.toJson(request))
        return new CreateResponse("accepted")
    }
}
