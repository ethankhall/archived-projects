package io.ehdev.timetracker.core.project

import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.permissions.Permissions
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.discount.DiscountFactory
import io.ehdev.timetracker.core.project.rate.Rate

class ProjectImpl extends PreformActionBaseImpl implements Project, Storable {

    Integer id

    @Delegate
    Rate rate

    @Delegate
    Discount discount = DiscountFactory.getNoDiscount()

    String name
    List<LineItemEntry> lineItems = []
    Permissions permissions;

    public List<LineItemEntry> getEntries(){
        return lineItems
    }

    BigDecimal getAmount(){
        return getAmount(lineItems)
    }
}
