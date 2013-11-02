package io.ehdev.easyinvoice.lineitem

import java.math.RoundingMode

class HourlyLineItemImpl extends LineItemImpl{

    BigDecimal hourlyRate
    Double hours

    HourlyLineItemImpl(BigDecimal hourlyRate, double hours) {
        this(hourlyRate, hours, "")
    }

    HourlyLineItemImpl(BigDecimal hourlyRate, double hours, def category) {
        super(BigDecimal.ZERO, category)
        this.hourlyRate = hourlyRate
        this.hours = hours
    }

    @Override
    BigDecimal getAmount() {
        def calculatedValue = hourlyRate.multiply(BigDecimal.valueOf(hours))
        return calculatedValue.setScale(3, RoundingMode.HALF_EVEN);
    }

}
