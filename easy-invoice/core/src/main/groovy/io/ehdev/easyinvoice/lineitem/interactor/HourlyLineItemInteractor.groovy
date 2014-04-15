package io.ehdev.easyinvoice.lineitem.interactor

import io.ehdev.easyinvoice.lineitem.HourlyLineItem

import java.math.RoundingMode

class HourlyLineItemInteractor extends LineItemInteractor {
    private final HourlyLineItem lineItem

    public HourlyLineItemInteractor(HourlyLineItem lineItem) {
        this.lineItem = lineItem
    }

    @Override
    BigDecimal calculateAmount() {
        def calculatedValue = lineItem.hourlyRate.multiply(BigDecimal.valueOf(lineItem.hours.getStandardMinutes() / 60))
        return calculatedValue.setScale(3, RoundingMode.HALF_EVEN);
    }

    @Override
    BigDecimal generateAmountDueForTaxes(double taxPercentage) {
        return generateAmountDueForTaxes(taxPercentage, lineItem)
    }
}
