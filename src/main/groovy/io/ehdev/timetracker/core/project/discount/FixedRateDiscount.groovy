package io.ehdev.timetracker.core.project.discount

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import java.math.RoundingMode

@Entity
@DiscriminatorValue(value="fixed")
class FixedRateDiscount extends Discount{

    FixedRateDiscount(BigDecimal discountRate){
        discountValue = discountRate
    }

    @Override
    BigDecimal getDiscountAmount(BigDecimal preRate){
        return discountValue.min(preRate).setScale(2, RoundingMode.HALF_EVEN)
    }

}
