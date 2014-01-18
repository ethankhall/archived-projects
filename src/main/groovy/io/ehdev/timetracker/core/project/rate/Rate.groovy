package io.ehdev.timetracker.core.project.rate
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.project.ProjectImpl

import javax.persistence.*

@Entity
@Inheritance
@Table
@DiscriminatorColumn(name="rate_selector", discriminatorType=DiscriminatorType.STRING)

@JsonAutoDetect()
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes([
    @JsonSubTypes.Type(FixedBidRate.class),
    @JsonSubTypes.Type(HourlyRate.class)
])
abstract class Rate {

    @Id
    @GeneratedValue
    @JsonIgnore
    Integer id

    @Column
    BigDecimal rateValue

    BigDecimal getAmount(ProjectImpl project){
        return getAmount(project.getEntries())
    }
    abstract BigDecimal getAmount(List<LineItemEntry> entries)
}
