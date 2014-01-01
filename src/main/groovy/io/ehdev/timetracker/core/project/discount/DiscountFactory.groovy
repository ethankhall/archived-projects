package io.ehdev.timetracker.core.project.discount

public class DiscountFactory {

    public static Discount getFlatRateDiscount(BigDecimal rate){
        return new FixedRateDiscount(rate)
    }

    public static Discount getPercentDiscount(BigDecimal percent){
        return new PercentDiscount(percent)
    }

    public static Discount getNoDiscount() {
        return getFlatRateDiscount(0)
    }
}
