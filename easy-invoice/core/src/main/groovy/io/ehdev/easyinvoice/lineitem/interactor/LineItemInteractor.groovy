package io.ehdev.easyinvoice.lineitem.interactor

import io.ehdev.easyinvoice.lineitem.FlatLineItem
import io.ehdev.easyinvoice.lineitem.HourlyLineItem
import io.ehdev.easyinvoice.lineitem.LineItem

import java.math.RoundingMode

abstract class LineItemInteractor {

    BigDecimal generateAmountDueForTaxes(double taxPercentage, LineItem lineItem) {
        if(!lineItem.taxEnabled) {
            return BigDecimal.ZERO.setScale(3)
        } else {
            return calculateTaxedAmount(calculateAmount(), taxPercentage).setScale(3, RoundingMode.HALF_EVEN);
        }
    }

    abstract BigDecimal calculateAmount()
    abstract BigDecimal generateAmountDueForTaxes(double taxPercentage)

    static protected BigDecimal calculateTaxedAmount(BigDecimal calculatedAmount, double taxPercentage) {
        calculatedAmount.multiply(BigDecimal.valueOf(taxPercentage / 100))
    }

    static LineItemInteractor getInteractor(LineItem item) {
        if(null == item){
            throw new NullPointerException();
        }

        if(item instanceof HourlyLineItem) {
            return new HourlyLineItemInteractor(item)
        } else if(item instanceof FlatLineItem){
            return new FlatLineItemInteractor(item)
        }
    }
}
