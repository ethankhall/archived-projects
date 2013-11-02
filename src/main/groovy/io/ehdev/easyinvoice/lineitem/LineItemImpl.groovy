package io.ehdev.easyinvoice.lineitem

import org.joda.time.DateTime
import java.math.RoundingMode

class LineItemImpl implements LineItem {
    private def category = ""
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

    def BigDecimal getAmount(double taxPercentage){
        BigDecimal calculatedAmount = amount;

        if(taxEnabled){
            calculatedAmount = calculateTaxedAmount(calculatedAmount, taxPercentage);
        }
        return calculatedAmount.setScale(3, RoundingMode.HALF_EVEN);
    }

    static protected BigDecimal calculateTaxedAmount(BigDecimal calculatedAmount, double taxPercentage) {
        calculatedAmount.multiply(BigDecimal.valueOf(1 + taxPercentage / 100))
    }
}
