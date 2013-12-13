package io.ehdev.timetracker.core.project.rate
import io.ehdev.timetracker.core.entry.LineItemEntry

import java.math.RoundingMode

class FixedBidRate extends Rate {

    BigDecimal rate

    @Override
    BigDecimal getAmount(List<LineItemEntry> entries) {
        return rate.setScale(2, RoundingMode.HALF_EVEN)
    }
}

