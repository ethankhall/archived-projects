package io.ehdev.timetracker.core.project

import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.rate.Rate

class Project {

    private String name
    private Rate rate
    private Discount discount
    private List<LineItemEntry> lineItems

    public List<LineItemEntry> getEntries(){
        return lineItems
    }

    public BigDecimal getAmount(){
        def baseRate = rate.getAmount(lineItems)
        return baseRate.subtract(discount.getDiscountAmount(baseRate))
    }

}
