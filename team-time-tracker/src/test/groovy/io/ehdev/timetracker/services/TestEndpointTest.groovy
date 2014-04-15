package io.ehdev.timetracker.services
import io.ehdev.timetracker.clock.FluxCapacitor
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class TestEndpointTest {

    TestEndpoint endpoint
    DateTime now = DateTime.now()

    @BeforeMethod
    public void setup() {
        endpoint = new TestEndpoint()
        endpoint.setClock(new FluxCapacitor(now))
    }

    @Test
    public void testGetExampleData() throws Exception {
        assertThat(endpoint.getExampleData()).isEqualTo(
                [ createdAt: ISODateTimeFormat.dateTime().print(now), output: 'this is a test resource']
        )
    }
}
