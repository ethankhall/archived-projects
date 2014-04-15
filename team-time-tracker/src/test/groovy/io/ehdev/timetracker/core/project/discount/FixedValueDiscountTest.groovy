package io.ehdev.timetracker.core.project.discount

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class FixedValueDiscountTest {

    @Test
    public void testSubtraction() throws Exception {
        Discount discount = DiscountFactory.getFlatRateDiscount(10)
        assertThat(discount.getDiscountAmount(100)).isEqualTo(10.00)
        assertThat(discount.getDiscountAmount(3)).isEqualTo(3.00)
    }
}
