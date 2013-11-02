package io.ehdev.easyinvoice.lineitem

import org.joda.time.DateTime
import java.math.RoundingMode

class LineItemImpl implements LineItem {

    def category = ""
    private def amount
    private def dateFinished = DateTime.now()
    def taxEnabled = true
    final def id = UUID.randomUUID() as String

    LineItemImpl(BigDecimal value) {
        this.amount = value
    }

    LineItemImpl(BigDecimal value, def category) {
        this.amount = value
        this.category = category;
    }

    static protected BigDecimal calculateTaxedAmount(BigDecimal calculatedAmount, double taxPercentage) {
        calculatedAmount.multiply(BigDecimal.valueOf(taxPercentage / 100))
    }

    @Override
    BigDecimal getAmountDueForTaxes(double taxPercentage) {
        if(!taxEnabled) {
            return BigDecimal.ZERO.setScale(3)
        } else {
            return calculateTaxedAmount(getAmount(), taxPercentage).setScale(3, RoundingMode.HALF_EVEN);
        }
    }

    @Override
    BigDecimal getAmount() {
        return amount.setScale(3, RoundingMode.HALF_EVEN);
    }
}
