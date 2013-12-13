package io.ehdev.timetracker.core.project.discount

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class PercentDiscountTest {

    @Test
    public void testRateOfDiscountIsCorrect_withDiscountOf50Percent() throws Exception {
        def discount = new PercentDiscount(BigDecimal.valueOf(0.5))
        assertThat(discount.getDiscountAmount(100)).isEqualTo(50.00)
        assertThat(discount.getDiscountAmount(25)).isEqualTo(12.50)
    }

    @Test
    public void testRateOfDiscountIsCorrect_withDiscountOf15Percent() throws Exception {
        def discount = new PercentDiscount(BigDecimal.valueOf(0.15))
        assertThat(discount.getDiscountAmount(100)).isEqualTo(15.00)
        assertThat(discount.getDiscountAmount(50)).isEqualTo(7.50)
    }

    @Test
    public void testHavingADiscountRateHigherThan100Percent() throws Exception {
        def discount = new PercentDiscount(BigDecimal.valueOf(1.10))
        assertThat(discount.getDiscountAmount(100)).isEqualTo(100.00)
        assertThat(discount.getDiscountAmount(50)).isEqualTo(50.00)
    }
}
