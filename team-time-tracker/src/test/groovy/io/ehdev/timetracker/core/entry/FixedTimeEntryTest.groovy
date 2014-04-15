package io.ehdev.timetracker.core.entry

import org.joda.time.DateTime
import org.joda.time.DateTimeUtils
import org.joda.time.Duration
import org.joda.time.Period
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class FixedTimeEntryTest {

    @Test
    public void testCreatingFixedTimeEntry() throws Exception {
        def baseTime = DateTime.now()
        FixedTimeEntry entry = new FixedTimeEntry(startTime: baseTime.minusHours(1), endTime: baseTime);
        assertThat(entry.getDuration()).isEqualTo(new Duration(60 * 60 * 1000))
    }

     @Test
     public void testNotSettingEndTime () throws Exception {
         DateTimeUtils.setCurrentMillisFixed(DateTime.now().getMillis())
         def entry = new FixedTimeEntry(startTime: DateTime.now().minusHours(10))
         assertThat(entry.getDuration().getMillis()).isEqualTo(Period.hours(10).toStandardDuration().getMillis())
         DateTimeUtils.setCurrentMillisSystem()
     }

    @Test(expectedExceptions = StartAndEntTimeAreOutOfOrderException.class)
    public void testSettingTheTimesInTheWrongOrder() throws Exception {
        def baseTime = DateTime.now()
        new FixedTimeEntry(startTime: baseTime.plusHours(1), endTime: baseTime)
    }

    @Test(expectedExceptions = BadStartTimeException.class)
    public void testSettingNullStartTimeWillFail() throws Exception {
        new FixedTimeEntry(startTime: null)
    }

    @Test
    public void testSettingTheTimes() throws Exception {
        def now = DateTime.now()
        def entry = new FixedTimeEntry(startTime: now.minusHours(10))
        entry.setEndTime(now)
        entry.setStartTime(now.minusMinutes(2))
        entry.setTimes(now.minusHours(1), now.plusHours(1))
    }
}
