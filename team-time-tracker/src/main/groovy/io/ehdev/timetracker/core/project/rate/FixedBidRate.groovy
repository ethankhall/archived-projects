package io.ehdev.timetracker.core.project.rate
import com.fasterxml.jackson.annotation.JsonTypeName
import io.ehdev.timetracker.core.entry.LineItemEntry

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import java.math.RoundingMode

@Entity
@DiscriminatorValue(value="fixed")
@JsonTypeName("FIXED")
class FixedBidRate extends Rate {

    FixedBidRate(BigDecimal value) {
        this.rateValue = value
    }

    FixedBidRate() { }

    @Override
    BigDecimal getAmount(List<LineItemEntry> entries) {
        return rateValue.setScale(2, RoundingMode.HALF_EVEN)
    }

    static final FixedBidRate ZERO = new FixedBidRate(rateValue: 0)

}

