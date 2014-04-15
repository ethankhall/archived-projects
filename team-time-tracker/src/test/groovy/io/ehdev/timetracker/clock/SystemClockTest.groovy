package io.ehdev.timetracker.clock

import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class SystemClockTest {

    @Test
    public void testGetNow() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(1000)
        def clock = new SystemClock()
        assertThat(clock.getNow()).isEqualTo(new DateTime(1000))
        DateTimeUtils.setCurrentMillisSystem()
    }
}
