package io.ehdev.easyinvoice.invoice

import io.ehdev.easyinvoice.lineitem.LineItemImpl

class InvoiceImpl implements Invoice {

    def lineItems = [];

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
}
