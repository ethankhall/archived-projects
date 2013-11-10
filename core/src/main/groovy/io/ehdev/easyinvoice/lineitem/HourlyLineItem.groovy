package io.ehdev.easyinvoice.lineitem

import org.joda.time.Duration

class HourlyLineItem extends LineItem {

    BigDecimal hourlyRate = BigDecimal.ZERO
    Duration hours = Duration.ZERO

    BigDecimal calculateAmount(){
        return hourlyRate.multiply(hours.getStandardMinutes() / 60)
    }

}
