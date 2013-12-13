package io.ehdev.timetracker.core.project.discount

interface Discount {
    /**
     * This will return a maximun of perRate
     * @param preRate the rate that would be issued before the discount
     * @return Math.min(preRate, calculatedRate())
     */
    BigDecimal getDiscountAmount(BigDecimal preRate)
}
