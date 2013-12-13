package io.ehdev.timetracker.core.project.rate

import groovy.mock.interceptor.MockFor
import io.ehdev.timetracker.core.project.Project
import org.testng.annotations.Test

class FixedBidRateTest {

    @Test
    public void testFixedBids() throws Exception {
        def mock = new MockFor(Project)
        mock.demand.getEntries() { [] }

        mock.use {
            def rate = new FixedBidRate(rate: 10)
            rate.getAmount(new Project())
        }

    }
}
