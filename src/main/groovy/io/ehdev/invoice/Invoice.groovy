package io.ehdev.invoice

interface Invoice {
    def addLineItem(LineItem lineItem);
    def addLineItems(List<LineItem> lineItems);

    List<LineItem> getLineItemsForCategory(String s)
    List<LineItem> getLineItemsWithoutCategory()

    List<String> getCategories()
}
