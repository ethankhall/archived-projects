package io.ehdev.easyinvoice.lineitem

import io.ehdev.easyinvoice.lineitem.interactor.FlatLineItemInteractor
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import java.math.RoundingMode

import static org.fest.assertions.Assertions.assertThat

public class LineItemImplTest {

    def lineItem
    def lineItemInteractor

    @BeforeMethod
    void preTestSetup() {
        lineItem = new FlatLineItem(['amount' :BigDecimal.valueOf(1.2345)]);
        lineItemInteractor = new FlatLineItemInteractor(lineItem)
    }

    @Test
    public void testThatExtendedValueShouldBeTruncated() throws Exception {
        assertThat(lineItemInteractor.calculateAmount()).isEqualTo(BigDecimal.valueOf(1.234))
    }

    @Test
    public void testSetTaxable() throws Exception {
        lineItem.setTaxEnabled(true)
        assertThat(lineItem.taxEnabled).is(true)
        assertThat(lineItemInteractor.generateAmountDueForTaxes(12.3456)).isEqualTo(BigDecimal.valueOf(1.2345 * 0.123456).setScale(3, RoundingMode.HALF_EVEN))
        lineItem.setTaxEnabled(false)
        assertThat(lineItem.taxEnabled).is(false)
    }
}
