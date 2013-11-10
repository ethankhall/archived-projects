package io.ehdev.easyinvoice.interfaces
import com.fasterxml.jackson.databind.ObjectMapper
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

public class InvoiceWrapperTest {

    @Test
    public void testSerialization() throws Exception {
        ObjectMapper om = ObjectMapper.newInstance()
        StringWriter sw = StringWriter.newInstance()
        def invoice = InvoiceWrapper.createSampleInvoice("http://localhost")
        om.writeValue(sw, invoice)
        sw.close()
        def calculated = om.readValue(sw.toString(), InvoiceWrapper.class)
        assertThat(invoice.toString()).isEqualTo(calculated.toString())
    }
}
