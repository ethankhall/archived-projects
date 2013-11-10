package io.ehdev.easyinvoice.services.converter
import io.ehdev.easyinvoice.accessor.ContactInfoAccessor
import io.ehdev.easyinvoice.accessor.InvoiceAccessor
import io.ehdev.easyinvoice.accessor.LineItemAccessor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BackendConverter {

    @Autowired
    LineItemAccessor lineItemAccessor

    @Autowired
    InvoiceAccessor invoiceAccessor

    @Autowired
    ContactInfoAccessor contactInfoConverter

    @Autowired
    @Delegate
    LineItemConverter lineItemConverter

}
