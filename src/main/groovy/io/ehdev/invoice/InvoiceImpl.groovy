package io.ehdev.invoice

class InvoiceImpl implements Invoice {

    def lineItems = [];

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
    List<LineItem> getLineItemsForCategory(String category) {
        return lineItems.findAll {
            it.category?.equals(category)
        } as List<LineItem>
    }

    @Override
    List<LineItem> getLineItemsWithoutCategory() {
        return getLineItemsForCategory("")
    }

    @Override
    List<String> getCategories() {
        return lineItems.collect{
            it.category
        }.unique() as List<String>
    }
}
