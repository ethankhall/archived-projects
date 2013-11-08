package io.ehdev.easyinvoice.config

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import io.ehdev.easyinvoice.contact.Address
import io.ehdev.easyinvoice.contact.ContactInfo
import io.ehdev.easyinvoice.invoice.Invoice
import io.ehdev.easyinvoice.invoice.InvoiceImpl
import io.ehdev.easyinvoice.lineitem.HourlyLineItem
import org.joda.time.DateTime
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
        def invoice = new InvoiceImpl()
        invoice.lineItems << new HourlyLineItem(hourlyRate: BigDecimal.valueOf(40), hours: 12, description: "Desc 1")
        invoice.lineItems << new HourlyLineItem(hourlyRate: BigDecimal.valueOf(45), hours: 8)
        invoice.setCustomerInfo(createCustomer())
        invoice.setMerchantInfo(createMerchant())
        invoice.setIssuedDate(DateTime.now())
        invoice.setDueDate(DateTime.now().plusDays(7))
        return invoice;
    }

    private ContactInfo createCustomer() {
        def customer = new ContactInfo()
        customer.setBusinessName("Definitive Edge Studios Ltd.")
        customer.setFirstName("Martin")
        customer.setLastName("Toomey")
        def addressBuilder = new Address.Builder()
            .setCity("Waterford")
            .setCountyCode("Ireland")
            .setLine1("63 Bayview")
            .build()

        customer.setAddress(addressBuilder)
        return customer
    }

    private ContactInfo createMerchant() {
        def customer = new ContactInfo()
        customer.setBusinessName("Brightworks")
        def addressBuilder = new Address.Builder()
                .setCity("Dublin")
                .setCountyCode("Ireland")
                .setLine1("17 Corrig Road")
                .setLine2("Sandyford Industrial Estate")
                .setPostalCode("18")
                .build()

        customer.setAddress(addressBuilder)
        return customer
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    @ResponseBody
    public def createNewPost(@RequestBody Invoice request){
        log.info("'Saving' data")
        log.info(JsonOutput.toJson(request))
        return [ 'status' : 'accepted' ]
    }
}
