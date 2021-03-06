package io.ehdev.timetracker.core.project

import com.fasterxml.jackson.annotation.JsonIgnore
import io.ehdev.timetracker.core.PreformActionBaseImpl
import io.ehdev.timetracker.core.Storable
import io.ehdev.timetracker.core.company.CompanyImpl
import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.permissions.ExtendedPermissions
import io.ehdev.timetracker.core.permissions.UserProjectPermissions
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.discount.DiscountFactory
import io.ehdev.timetracker.core.project.rate.Rate

import javax.persistence.*

@Entity
@Table
class ProjectImpl extends PreformActionBaseImpl implements Project, Storable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonIgnore
    Integer id

    @Column
    String uuid

    @Delegate
    @OneToOne(cascade = CascadeType.ALL)
    Rate rate

    @Delegate
    @OneToOne(cascade = CascadeType.ALL)
    Discount discount = DiscountFactory.getNoDiscount()

    @Column
    String name

    @OneToMany
    List<LineItemEntry> lineItems = []

    @OneToMany
    List<UserProjectPermissions> permissions = [];

    @ManyToOne
    @JoinColumn(name = 'company_id')
    CompanyImpl company

    public List<ExtendedPermissions> getPermissions(){
        if(company && company.permissions){
            return permissions + company.permissions
        } else {
            return permissions
        }
    }

    public List<LineItemEntry> getEntries(){
        return lineItems
    }

    BigDecimal getAmount(){
        return getAmount(lineItems)
    }

}
