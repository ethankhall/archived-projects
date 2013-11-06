package io.ehdev.easyinvoice.lineitem.interactor
import io.ehdev.easyinvoice.lineitem.FlatLineItem

import java.math.RoundingMode

class FlatLineItemInteractor extends LineItemInteractor {

    private final FlatLineItem lineItem

    FlatLineItemInteractor(FlatLineItem lineItem) {
        this.lineItem = lineItem
    }

    @Override
    BigDecimal generateAmountDueForTaxes(double taxPercentage) {
        return generateAmountDueForTaxes(taxPercentage, lineItem)
    }

    @Override
    BigDecimal calculateAmount() {
        return lineItem.amount.setScale(3, RoundingMode.HALF_EVEN);
    }

}
