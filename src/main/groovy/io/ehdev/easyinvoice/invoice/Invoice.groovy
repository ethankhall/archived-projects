package io.ehdev.easyinvoice.invoice

import io.ehdev.easyinvoice.contact.ContactInfo
import io.ehdev.easyinvoice.lineitem.LineItemImpl
import org.joda.time.DateTime

interface Invoice {
    def addLineItem(LineItemImpl lineItem);
    def addLineItems(List<LineItemImpl> lineItems);

    List<LineItemImpl> getLineItemsForCategory(String s)
    List<LineItemImpl> getLineItemsWithoutCategory()

    List<String> getCategories()

    def getRemoveLineItem(String id)
    def disableTaxForCategory(String category)
    def enableTaxForCategory(String category)

    def setTaxRate(double taxPercentage)

    BigDecimal getAmount()
    BigDecimal getTaxDue()

    void setCustomerInfo(ContactInfo customerContact)
    ContactInfo getCustomerInfo()

    void setMerchantInfo(ContactInfo customerContact)
    ContactInfo getMerchantInfo()

    void setDueDate(DateTime dateDue)
    DateTime getDueDate()

    void setIssuedDate(DateTime dateDue)
    DateTime getIssuedDate()
}
