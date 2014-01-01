package io.ehdev.timetracker.core.project.rate
import io.ehdev.timetracker.core.entry.LineItemEntry

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import java.math.RoundingMode

@Entity
@DiscriminatorValue(value="fixed")
class FixedBidRate extends Rate {

    @Override
    BigDecimal getAmount(List<LineItemEntry> entries) {
        return rateValue.setScale(2, RoundingMode.HALF_EVEN)
    }

}

