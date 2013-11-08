package io.ehdev.easyinvoice.rest

import io.ehdev.easyinvoice.contact.ContactInfo
import org.joda.time.DateTime

class InvoiceTransform {

    def id;
    def lineItems = [];
    def taxRateAsPercent
    ContactInfo customerInfo
    ContactInfo merchantInfo
    DateTime dueDate
    DateTime issuedDate
    String invoicePrefix
    String invoiceNumber
}
