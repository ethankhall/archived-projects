package io.ehdev.easyinvoice.invoice

import io.ehdev.easyinvoice.lineitem.LineItem

interface Invoice {
    def addLineItem(LineItem lineItem);
    def addLineItems(List<LineItem> lineItems);

    List<LineItem> getLineItemsForCategory(String s)
    List<LineItem> getLineItemsWithoutCategory()

    List<String> getCategories()

    def getRemoveLineItem(String id)
}
