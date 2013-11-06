package io.ehdev.easyinvoice.lineitem

import io.ehdev.easyinvoice.lineitem.interactor.HourlyLineItemInteractor
import org.testng.annotations.Test

import java.math.RoundingMode

import static org.fest.assertions.Assertions.assertThat

public class HourlyLineItemTest {

    @Test
    public void testChangeHourValue() throws Exception {
        HourlyLineItem lineItem = new HourlyLineItem(BigDecimal.ONE, 1);
        def interactor = new HourlyLineItemInteractor(lineItem)
        assertThat(interactor.calculateAmount()).isEqualTo(BigDecimal.ONE.setScale(3));
        lineItem.setHours(100.234);
        lineItem.setHourlyRate(BigDecimal.valueOf(2.1));
        assertThat(interactor.calculateAmount()).isEqualTo(BigDecimal.valueOf(2.1 * 100.234).setScale(3, RoundingMode.HALF_EVEN));
    }
}
