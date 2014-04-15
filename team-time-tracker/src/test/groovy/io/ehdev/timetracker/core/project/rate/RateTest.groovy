package io.ehdev.timetracker.core.project.rate
import io.ehdev.timetracker.JsonTestHelper
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class RateTest {

    @Test
    public void serializationOfHourlyRate() throws Exception {
        def discountString = JsonTestHelper.objectToString(new HourlyRate(12.4))

        def deser = JsonTestHelper.stringToObject(discountString, Rate.class)
        assertThat(deser).isInstanceOf(HourlyRate.class)
    }

    @Test
    public void serializationOfFixedBidRate() throws Exception {
        def discountString = JsonTestHelper.objectToString(new FixedBidRate(12.4))

        def deser = JsonTestHelper.stringToObject(discountString, Rate.class)
        assertThat(deser).isInstanceOf(FixedBidRate.class)
    }
}
