package io.ehdev.easyinvoice.accessor

import io.ehdev.easyinvoice.lineitem.LineItem

public class LineItemInMemoryAccessor implements LineItemAccessor{
    def lineItems = [:]

    @Override
    LineItem get(id) {
        lineItems[id]
    }

    @Override
    def save(LineItem lineItem) {
        lineItems[lineItem.id] = lineItem
    }
}
