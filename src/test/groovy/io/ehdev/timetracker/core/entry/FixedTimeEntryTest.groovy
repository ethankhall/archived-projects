package io.ehdev.timetracker.core.entry

import org.joda.time.DateTime
import org.joda.time.Duration
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class FixedTimeEntryTest {

    @Test
    public void testCreatingFixedTimeEntry() throws Exception {
        def baseTime = DateTime.now()
        FixedTimeEntry entry = new FixedTimeEntry(startTime: baseTime.minusHours(1), endTime: baseTime);
        assertThat(entry.getDuration()).isEqualTo(new Duration(60 * 60 * 1000))
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testNotSettingStartTime_throwsRuntimeException() throws Exception {
        new FixedTimeEntry(endTime: DateTime.now())
    }

     @Test(expectedExceptions = RuntimeException.class)
     public void testNotSettingEndTime_throwsRuntimeException() throws Exception {
         new FixedTimeEntry(startTime: DateTime.now())
     }

    @Test(expectedExceptions = RuntimeException.class)
    public void testSettingTheTimesInTheWrongOrder() throws Exception {
        def baseTime = DateTime.now()
        new FixedTimeEntry(startTime: baseTime.plusHours(1), endTime: baseTime)
    }
}
