package io.ehdev.easyinvoice.interfaces

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class LineItemWrapperTest {
    @Test
    public void testSerializeHourly() throws Exception {
        def contactInfo = HourlyLineItemWrapper.createTestingHourlyLineItem()
        def calculated = WrapperTestHelper.createJsonConverter(contactInfo, HourlyLineItemWrapper.class)
        assertThat(contactInfo.toString()).isEqualTo(calculated.toString())
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSerializeHourlyIntoFlat_shouldThrow() throws Exception {
        def contactInfo = HourlyLineItemWrapper.createTestingHourlyLineItem()
        def calculated = WrapperTestHelper.createJsonConverter(contactInfo, FlatLineItemWrapper.class)
        assertThat(contactInfo.toString()).isEqualTo(calculated.toString())
    }

    @Test
    public void testSerializeList() throws Exception {
        def hourlyInfo = HourlyLineItemWrapper.createTestingHourlyLineItem()
        def flatInfo = FlatLineItemWrapper.createTestingFlatLineItem()
        def wrappers = [hourlyInfo, flatInfo ] as LineItemWrapper[]

        def calculated = WrapperTestHelper.createJsonConverter(wrappers, LineItemWrapper[].class)
        assertThat(calculated[0].toString()).contains(hourlyInfo.toString())
        assertThat(calculated[1].toString()).contains(flatInfo.toString())
    }
}
