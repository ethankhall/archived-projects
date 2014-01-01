package io.ehdev.timetracker.core.project.rate
import io.ehdev.timetracker.core.entry.LineItemEntry

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(value="hourly")
class HourlyRate extends Rate {

    @Override
    BigDecimal getAmount(List<LineItemEntry> entries) {
        BigDecimal value = BigDecimal.ZERO
        entries.each {
            value = value.add(it.getDuration().getMillis() / 1000 / 60 / 60 * rateValue)
        }

        return value
    }
}
