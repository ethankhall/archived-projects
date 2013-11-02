package io.ehdev.easyinvoice.lineitem

import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

public class LineItemTest {

    @Test
    public void testThatExtendedValueShouldBeTruncated() throws Exception {
        def lineItem = new LineItem(BigDecimal.valueOf(1.2345));
        assertThat(lineItem.getAmount()).isEqualTo(BigDecimal.valueOf(1.23))
    }
}
