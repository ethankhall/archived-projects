package io.ehdev.timetracker.core.project.rate

import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.project.ProjectImpl

import javax.persistence.*

@Entity
@Inheritance
@Table
@DiscriminatorColumn(name="rate_selector", discriminatorType=DiscriminatorType.STRING)
abstract class Rate {

    @Id
    @GeneratedValue
    Integer id

    @Column
    BigDecimal rateValue

    BigDecimal getAmount(ProjectImpl project){
        return getAmount(project.getEntries())
    }
    abstract BigDecimal getAmount(List<LineItemEntry> entries)
}
