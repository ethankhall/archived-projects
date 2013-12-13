package io.ehdev.timetracker.core.project.rate

import io.ehdev.timetracker.core.entry.FixedTimeEntry
import io.ehdev.timetracker.core.project.Project
import org.joda.time.DateTime
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class HourlyRateTest {

    DateTime now

    @BeforeMethod
    public void setup() {
        now = DateTime.now()
    }

    @Test
    public void testGettingTheValueForAHourlyRate() throws Exception {
        def hourlyRate = new HourlyRate(hourlyRate: 10.00)
        def project = new Project(lineItems: [ new FixedTimeEntry(startTime: now, endTime: now.plusHours(1))])
        assertThat(hourlyRate.getAmount(project)).isEqualTo(10.00)
        project = new Project(lineItems: [ new FixedTimeEntry(startTime: now, endTime: now.plusHours(2))])
        assertThat(hourlyRate.getAmount(project)).isEqualTo(20.00)
    }
}
