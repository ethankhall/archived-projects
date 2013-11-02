package io.ehdev.easyinvoice.lineitem

public interface LineItem {

    def BigDecimal getAmount(double taxPercentage);

}