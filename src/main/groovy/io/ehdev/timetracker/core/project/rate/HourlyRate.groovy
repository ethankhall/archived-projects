package io.ehdev.timetracker.core.project.rate
import io.ehdev.timetracker.core.entry.LineItemEntry

class HourlyRate extends Rate {

    BigDecimal hourlyRate

    @Override
    BigDecimal getAmount(List<LineItemEntry> entries) {
        BigDecimal value = BigDecimal.ZERO
        entries.each {
            value = value.add(it.getDuration().getMillis() / 1000 / 60 / 60 * hourlyRate)
        }

        return value
    }
}
