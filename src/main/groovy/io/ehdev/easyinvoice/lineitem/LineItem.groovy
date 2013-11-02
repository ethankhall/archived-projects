package io.ehdev.easyinvoice.lineitem

public interface LineItem {

    def BigDecimal getAmountDueForTaxes(double taxPercentage);
    def BigDecimal getAmount();

}