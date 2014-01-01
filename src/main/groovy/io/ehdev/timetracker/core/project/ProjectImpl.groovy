package io.ehdev.timetracker.core.project
import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.permissions.ExtendedPermissions
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.discount.DiscountFactory
import io.ehdev.timetracker.core.project.rate.Rate

import javax.persistence.*

@Entity
@Table
class ProjectImpl extends PreformActionBaseImpl implements Project, Storable {

    @Id
    @GeneratedValue
    Integer id

    @Column
    String uuid

    @Delegate
    @ManyToOne
    Rate rate

    @Delegate
    @ManyToOne
    Discount discount = DiscountFactory.getNoDiscount()

    @Column
    String name

    @OneToMany
    List<LineItemEntry> lineItems = []

    @ManyToOne
    ExtendedPermissions permissions;

    public List<LineItemEntry> getEntries(){
        return lineItems
    }

    BigDecimal getAmount(){
        return getAmount(lineItems)
    }
}
