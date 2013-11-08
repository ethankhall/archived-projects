package io.ehdev.easyinvoice.invoice

import io.ehdev.easyinvoice.contact.ContactInfo
import io.ehdev.easyinvoice.lineitem.LineItem
import io.ehdev.easyinvoice.lineitem.interactor.LineItemInteractor
import org.joda.time.DateTime

class InvoiceImpl implements Invoice {

    def lineItems = [];
    def taxRateAsPercent
    ContactInfo customerInfo
    ContactInfo merchantInfo
    DateTime dueDate
    DateTime issuedDate
    String invoicePrefix
    String invoiceNumber
    final def id = UUID.randomUUID() as String

    @Override
    def addLineItem(LineItem lineItem) {
        lineItems << lineItem
    }

    @Override
    def addLineItems(List<LineItem> lineItems) {
        lineItems.each {
            addLineItem(it)
        }
    }

    @Override
    List<LineItem> findLineItemsForCategory(String category) {
        return lineItems.findAll {
            it.category?.equals(category)
        } as List<LineItem>
    }

    @Override
    List<LineItem> findLineItemsWithoutCategory() {
        return findLineItemsForCategory("")
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
    BigDecimal calculateAmount() {
        lineItems.sum{
            LineItemInteractor.getInteractor(it).calculateAmount()
        } as BigDecimal
    }

    @Override
    BigDecimal calculateTaxDue() {
        lineItems.sum{
            LineItemInteractor.getInteractor(it).generateAmountDueForTaxes(taxRateAsPercent)
        } as BigDecimal
    }

}
