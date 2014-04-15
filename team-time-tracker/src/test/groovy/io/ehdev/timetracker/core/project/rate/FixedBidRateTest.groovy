package io.ehdev.timetracker.core.project.rate

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class FixedBidRateTest {

    @Test
    public void testFixedBids() throws Exception {
        def rate = new FixedBidRate(rateValue: 10)
        assertThat(rate.getAmount([])).isEqualTo(10.00)
        rate = new FixedBidRate(rateValue: 100)
        assertThat(rate.getAmount([])).isEqualTo(100.00)

    }
}
