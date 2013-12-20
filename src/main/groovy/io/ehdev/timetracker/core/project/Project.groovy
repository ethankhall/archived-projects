package io.ehdev.timetracker.core.project

import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.permissions.Permissions
import io.ehdev.timetracker.core.project.rate.Rate

class Project {

    String name
    Rate rate
    Discount discount
    List<LineItemEntry> lineItems
    Permissions permissions;

    public List<LineItemEntry> getEntries(){
        return lineItems
    }

    public BigDecimal getAmount(){
        def baseRate = rate.getAmount(lineItems)
        return baseRate.subtract(discount.getDiscountAmount(baseRate))
    }

}
