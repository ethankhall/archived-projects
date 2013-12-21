package io.ehdev.timetracker.core.project.rate

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class FixedBidRateTest {

    @Test
    public void testFixedBids() throws Exception {
        def rate = new FixedBidRate(rate: 10)
        assertThat(rate.getAmount([])).isEqualTo(10.00)
        rate = new FixedBidRate(rate: 100)
        assertThat(rate.getAmount([])).isEqualTo(100.00)

    }
}
