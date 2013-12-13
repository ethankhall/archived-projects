package io.ehdev.timetracker.core.project.discount

import java.math.RoundingMode

class PercentDiscount implements Discount {

    BigDecimal discount

    PercentDiscount(BigDecimal discountPercent){
        discount = discountPercent
    }

    @Override
    BigDecimal getDiscountAmount(BigDecimal preRate) {
        def proposedDiscount = preRate.multiply(discount)
        return proposedDiscount.min(preRate).setScale(2, RoundingMode.HALF_EVEN)
    }
}
