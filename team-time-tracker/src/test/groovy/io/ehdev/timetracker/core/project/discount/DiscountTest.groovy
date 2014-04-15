package io.ehdev.timetracker.core.project.discount

import io.ehdev.timetracker.JsonTestHelper
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class DiscountTest {

    @Test
    public void serializationToFixedDiscount() throws Exception {
        def discountString = JsonTestHelper.objectToString(new FixedValueDiscount(1.2))

        def deser = JsonTestHelper.stringToObject(discountString, Discount.class)
        assertThat(deser).isInstanceOf(FixedValueDiscount.class)
    }

    @Test
    public void serializationToPercentDiscount() throws Exception {
        def discountString = JsonTestHelper.objectToString(new PercentDiscount(1.2))

        def deser = JsonTestHelper.stringToObject(discountString, Discount.class)
        assertThat(deser).isInstanceOf(PercentDiscount.class)
    }
}
