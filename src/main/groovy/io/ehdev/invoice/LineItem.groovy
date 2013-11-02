package io.ehdev.invoice

import org.joda.time.DateTime
import java.math.RoundingMode

class LineItem {
    def category = ""
    def amount
    def dateFinished = DateTime.now()
    def taxEnabled = true

    LineItem(BigDecimal value) {
        this.amount = value.setScale(3, RoundingMode.HALF_EVEN)
    }

    LineItem(BigDecimal value, def category) {
        this.amount = value.setScale(3, RoundingMode.HALF_EVEN)
        this.category = category;
    }
}
