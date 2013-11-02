package io.ehdev.easyinvoice.lineitem;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.fest.assertions.Assertions.assertThat;

public class HourlyLineItemTest {

    @BeforeMethod
    void preTestSetup() {

    }

    @Test
    public void testChangeHourValue() throws Exception {
        HourlyLineItem lineItem = new HourlyLineItem(BigDecimal.ONE, 1);
        assertThat(lineItem.getAmount()).isEqualTo(BigDecimal.ONE.setScale(2));
        lineItem.setHours(100.234);
        lineItem.setHourlyRate(BigDecimal.valueOf(2.1));
        assertThat(lineItem.getAmount()).isEqualTo(BigDecimal.valueOf(2.1 * 100.234).setScale(2, RoundingMode.HALF_EVEN));
    }
}
