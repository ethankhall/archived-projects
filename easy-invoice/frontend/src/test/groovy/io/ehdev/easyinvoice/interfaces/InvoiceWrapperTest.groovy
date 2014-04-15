package io.ehdev.easyinvoice.interfaces

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

public class InvoiceWrapperTest {

    @Test
    public void testSerialization() throws Exception {
        def invoice = InvoiceWrapper.createSampleInvoice("http://localhost")
        def calculated = WrapperTestHelper.createJsonConverter(invoice, InvoiceWrapper.class)
        assertThat(invoice.toString()).isEqualTo(calculated.toString())
    }
}
