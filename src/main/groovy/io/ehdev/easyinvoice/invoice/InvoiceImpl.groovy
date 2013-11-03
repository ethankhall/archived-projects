package io.ehdev.easyinvoice.invoice

import io.ehdev.easyinvoice.contact.ContactInfo
import io.ehdev.easyinvoice.lineitem.LineItemImpl

class InvoiceImpl implements Invoice {

    def lineItems = [];
    def taxRateAsPercent
    def customerContact;
    def merchantContact;

    @Override
    def addLineItem(LineItemImpl lineItem) {
        lineItems << lineItem
    }

    @Override
    def addLineItems(List<LineItemImpl> lineItems) {
        lineItems.each {
            addLineItem(it)
        }
    }

    @Override
    List<LineItemImpl> getLineItemsForCategory(String category) {
        return lineItems.findAll {
            it.category?.equals(category)
        } as List<LineItemImpl>
    }

    @Override
    List<LineItemImpl> getLineItemsWithoutCategory() {
        return getLineItemsForCategory("")
    }

    @Override
    List<String> getCategories() {
        return lineItems.collect{
            it.category
        }.unique() as List<String>
    }

    @Override
    def getRemoveLineItem(String id) {
        lineItems.removeAll {
            it.id.equals(id)
        }
    }

    @Override
    def disableTaxForCategory(String category) {
        lineItems.each {
            if(it.category?.equals(category)){
                it.setTaxEnabled(false)
            }
        }
    }

    @Override
    def enableTaxForCategory(String category) {
        lineItems.each {
            if(it.category?.equals(category)){
                it.setTaxEnabled(true)
            }
        }
    }

    @Override
    def setTaxRate(double taxPercentage) {
        taxRateAsPercent = taxPercentage
    }

    @Override
    BigDecimal getAmount() {
        lineItems.sum{
            it.getAmount()
        } as BigDecimal
    }

    @Override
    BigDecimal getTaxDue() {
        lineItems.sum{
            it.getAmountDueForTaxes(taxRateAsPercent)
        } as BigDecimal
    }

    @Override
    void setCustomerInfo(ContactInfo customerContact) {
        customerInfo = customerContact
    }

    @Override
    ContactInfo getCustomerInfo() {
        return customerInfo
    }

    @Override
    void setMerchantInfo(ContactInfo merchantInfo) {
        this.merchantInfo = merchantInfo
    }

    @Override
    ContactInfo getMerchantInfo() {
        return merchantInfo
    }
}
