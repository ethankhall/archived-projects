package io.ehdev.easyinvoice.lineitem

import org.testng.annotations.Test

import java.math.RoundingMode

import static org.fest.assertions.Assertions.assertThat

public class HourlyLineItemTest {

    @Test
    public void testChangeHourValue() throws Exception {
        HourlyLineItemImpl lineItem = new HourlyLineItemImpl(BigDecimal.ONE, 1);
        assertThat(lineItem.getAmount()).isEqualTo(BigDecimal.ONE.setScale(3));
        lineItem.setHours(100.234);
        lineItem.setHourlyRate(BigDecimal.valueOf(2.1));
        assertThat(lineItem.getAmount()).isEqualTo(BigDecimal.valueOf(2.1 * 100.234).setScale(3, RoundingMode.HALF_EVEN));
    }
}
