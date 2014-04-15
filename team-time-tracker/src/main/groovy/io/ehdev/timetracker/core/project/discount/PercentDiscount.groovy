package io.ehdev.timetracker.core.project.discount

import com.fasterxml.jackson.annotation.JsonTypeName

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import java.math.RoundingMode

@Entity
@DiscriminatorValue(value="percent")
@JsonTypeName("PERCENT")
class PercentDiscount extends Discount {

    PercentDiscount(BigDecimal discountPercent){
        discountValue = discountPercent
    }

    PercentDiscount() { }

    @Override
    BigDecimal getDiscountAmount(BigDecimal preRate) {
        def proposedDiscount = preRate.multiply(discountValue)
        return proposedDiscount.min(preRate).setScale(2, RoundingMode.HALF_EVEN)
    }
}
