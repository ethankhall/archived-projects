package io.ehdev.timetracker.core.project.discount

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import java.math.RoundingMode

@Entity
@DiscriminatorValue(value="percent")
class PercentDiscount extends Discount {

    PercentDiscount(BigDecimal discountPercent){
        discountValue = discountPercent
    }

    @Override
    BigDecimal getDiscountAmount(BigDecimal preRate) {
        def proposedDiscount = preRate.multiply(discountValue)
        return proposedDiscount.min(preRate).setScale(2, RoundingMode.HALF_EVEN)
    }
}
