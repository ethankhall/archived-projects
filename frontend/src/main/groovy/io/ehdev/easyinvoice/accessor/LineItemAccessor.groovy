package io.ehdev.easyinvoice.accessor

import io.ehdev.easyinvoice.lineitem.LineItem

interface LineItemAccessor {
    LineItem get(def id)
    def save(LineItem lineItem)
}
