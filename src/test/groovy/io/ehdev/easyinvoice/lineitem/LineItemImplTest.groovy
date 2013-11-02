package io.ehdev.easyinvoice.lineitem

import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.math.RoundingMode

import static org.fest.assertions.Assertions.assertThat

public class LineItemImplTest {

    def lineItem

    @BeforeMethod
    void preTestSetup() {
        lineItem = new LineItemImpl(BigDecimal.valueOf(1.2345));
    }

    @Test
    public void testThatExtendedValueShouldBeTruncated() throws Exception {
        assertThat(lineItem.getAmount(0)).isEqualTo(BigDecimal.valueOf(1.234))
    }

    @Test
    public void testSetTaxable() throws Exception {
        lineItem.setTaxEnabled(true)
        assertThat(lineItem.taxEnabled).is(true)
        assertThat(lineItem.getAmount(12.3456)).isEqualTo(BigDecimal.valueOf(1.2345 * 1.123456).setScale(3, RoundingMode.HALF_EVEN))
        lineItem.setTaxEnabled(false)
        assertThat(lineItem.taxEnabled).is(false)
    }
}
