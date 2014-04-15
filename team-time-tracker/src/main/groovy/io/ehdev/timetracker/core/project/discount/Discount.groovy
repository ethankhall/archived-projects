package io.ehdev.timetracker.core.project.discount

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

import javax.persistence.*

@Entity
@Inheritance
@Table
@DiscriminatorColumn(name="discount_selector", discriminatorType=DiscriminatorType.STRING)

@JsonAutoDetect()
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes([
    @JsonSubTypes.Type(FixedValueDiscount.class),
    @JsonSubTypes.Type(PercentDiscount.class)
])
abstract class Discount {

    @Id
    @GeneratedValue
    @JsonIgnore
    Integer id

    @Column
    BigDecimal discountValue


    /**
     * This will return a maximun of perRate
     * @param preRate the rateValue that would be issued before the discountValue
     * @return Math.min(preRate, calculatedRate())
     */
    abstract BigDecimal getDiscountAmount(BigDecimal preRate)
}
