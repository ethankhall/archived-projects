package io.ehdev.timetracker.core.project.discount

import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.Table

@Entity
@Inheritance
@Table
@DiscriminatorColumn(name="discount_selector", discriminatorType=DiscriminatorType.STRING)
abstract class Discount {

    @Id
    @GeneratedValue
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
