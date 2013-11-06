package io.ehdev.easyinvoice.invoice

import io.ehdev.easyinvoice.contact.ContactInfo
import io.ehdev.easyinvoice.lineitem.LineItem
import org.joda.time.DateTime

interface Invoice {
    def addLineItem(LineItem lineItem);
    def addLineItems(List<LineItem> lineItems);

    List<LineItem> findLineItemsForCategory(String s)
    List<LineItem> findLineItemsWithoutCategory()

    List<String> getCategories()

    def getRemoveLineItem(String id)
    def disableTaxForCategory(String category)
    def enableTaxForCategory(String category)

    def setTaxRate(double taxPercentage)

    BigDecimal calculateAmount()
    BigDecimal calculateTaxDue()

    void setCustomerInfo(ContactInfo customerContact)
    ContactInfo getCustomerInfo()

    void setMerchantInfo(ContactInfo customerContact)
    ContactInfo getMerchantInfo()

    void setDueDate(DateTime dateDue)
    DateTime getDueDate()

    void setIssuedDate(DateTime dateDue)
    DateTime getIssuedDate()

    void setInvoicePrefix(String prefix)
    String getInvoicePrefix()

    void setInvoiceNumber(String invoiceNumber)
    String getInvoiceNumber();
}
