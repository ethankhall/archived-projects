package io.ehdev.easyinvoice.lineitem

import java.math.RoundingMode

class HourlyLineItem extends LineItem{

    BigDecimal hourlyRate
    Double hours

    HourlyLineItem(BigDecimal hourlyRate, double hours) {
        this(hourlyRate, hours, "")
    }

    HourlyLineItem(BigDecimal hourlyRate, double hours, def category) {
        super(BigDecimal.ZERO, category)
        this.hourlyRate = hourlyRate
        this.hours = hours
    }

    @Override
    def BigDecimal getAmount(){
        def calculatedValue = hourlyRate.multiply(BigDecimal.valueOf(hours))
        return calculatedValue.setScale(2, RoundingMode.HALF_EVEN)
    }
}
