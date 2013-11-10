package io.ehdev.easyinvoice.interfaces

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class ContactInfoWrapperTest {
    @Test
    public void testSerialization() throws Exception {
        def contactInfo = ContactInfoWrapper.createSampleData()
        def calculated = WrapperTestHelper.createJsonConverter(contactInfo, ContactInfoWrapper.class)

        assertThat(contactInfo.toString()).isEqualTo(calculated.toString())
    }
}
