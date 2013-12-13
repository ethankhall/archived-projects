package io.ehdev.timetracker.core.project.discount

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class FixedRateDiscountTest {

    @Test
    public void testSubtraction() throws Exception {
        FixedRateDiscount discount = new FixedRateDiscount(10)
        assertThat(discount.getDiscountAmount(100)).isEqualTo(10.00)
        assertThat(discount.getDiscountAmount(3)).isEqualTo(3.00)
    }
}
