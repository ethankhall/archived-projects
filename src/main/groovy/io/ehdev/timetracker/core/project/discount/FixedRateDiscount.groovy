package io.ehdev.timetracker.core.project.discount

import java.math.RoundingMode

class FixedRateDiscount implements Discount{

    BigDecimal value

    FixedRateDiscount(BigDecimal discountRate){
        value = discountRate
    }

    @Override
    BigDecimal getDiscountAmount(BigDecimal preRate){
        return value.min(preRate).setScale(2, RoundingMode.HALF_EVEN)
    }

}
