package io.ehdev.easyinvoice.services.converter
import io.ehdev.easyinvoice.accessor.LineItemInMemoryAccessor
import io.ehdev.easyinvoice.interfaces.FlatLineItemWrapper
import io.ehdev.easyinvoice.interfaces.HourlyLineItemWrapper
import io.ehdev.easyinvoice.lineitem.FlatLineItem
import io.ehdev.easyinvoice.lineitem.HourlyLineItem
import org.joda.time.Duration
import org.testng.annotations.BeforeTest
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class LineItemConverterTest {

    LineItemInMemoryAccessor lineItemAccessor
    LineItemConverter lineItemConverter

    @BeforeTest
    public void setup(){
        lineItemAccessor = LineItemInMemoryAccessor.newInstance()

        lineItemConverter = LineItemConverter.newInstance()
        lineItemConverter.lineItemAccessor = lineItemAccessor
    }

    @Test
    public void testConvertingToHourlyLineItemWrapper() throws Exception {
        HourlyLineItem hourlyLineItem = HourlyLineItem.newInstance(
                [id: "1", hourlyRate: BigDecimal.TEN, hours: Duration.standardMinutes(1234)]
        )

        lineItemAccessor.save(hourlyLineItem)
        def convertedValue = lineItemConverter.convertLineItemToWrapper(lineItemAccessor.get("1"))
        assertThat(hourlyLineItem.id).isEqualTo(convertedValue.id)
        assertThat(convertedValue.amount).isEqualTo(hourlyLineItem.calculateAmount())
        assertThat(convertedValue.taxable).isEqualTo(hourlyLineItem.taxEnabled)
    }

    @Test
    public void testFlatLineItemConverter() throws Exception {
        FlatLineItem flatLineItem = FlatLineItem.newInstance(
                [id: "1", amount: BigDecimal.TEN]
        )

        lineItemAccessor.save(flatLineItem)
        def convertedValue = lineItemConverter.convertLineItemToWrapper(lineItemAccessor.get("1"))
        assertThat(flatLineItem.id).isEqualTo(convertedValue.id)
        assertThat(convertedValue.amount).isEqualTo(flatLineItem.amount)
        assertThat(convertedValue.taxable).isEqualTo(flatLineItem.taxEnabled)
    }

    @Test
    public void testGettingItemFromId() throws Exception {
        FlatLineItem flatLineItem = FlatLineItem.newInstance(
                [id: "1", amount: BigDecimal.TEN] )
        HourlyLineItem hourlyLineItem = HourlyLineItem.newInstance(
                [id: "2", hourlyRate: BigDecimal.TEN, hours: Duration.standardMinutes(1234)] )
        lineItemAccessor.save(flatLineItem, hourlyLineItem)
        assertThat(lineItemConverter.getWrapperFromId("1")).isInstanceOf(FlatLineItemWrapper.class)
        assertThat(lineItemConverter.getWrapperFromId("2")).isInstanceOf(HourlyLineItemWrapper.class)
    }

    @Test
    public void testHourlyLineItemWrapperToLineItem() throws Exception {
        def itemWrapper = HourlyLineItemWrapper.createTestingHourlyLineItem()
        def lineItem = lineItemConverter.convertWrapperToLineItem(itemWrapper)
        assertThat(lineItem).isInstanceOf(HourlyLineItem.class)
        assertThat(lineItem.category).isEqualTo(itemWrapper.category)
        assertThat(lineItem.description).isEqualTo(itemWrapper.description)
        assertThat(lineItem.taxEnabled).isEqualTo(itemWrapper.taxable)
        assertThat(lineItem.hourlyRate).isEqualTo(itemWrapper.hourlyRate)
        assertThat(lineItem.hours).isEqualTo(itemWrapper.hours)
    }

    @Test
    public void testFlatLineItemWrapperToFlatItem() throws Exception {
        def itemWrapper = FlatLineItemWrapper.createTestingFlatLineItem()
        def lineItem = lineItemConverter.convertWrapperToLineItem(itemWrapper)
        assertThat(lineItem).isInstanceOf(FlatLineItem.class)
        assertThat(lineItem.category).isEqualTo(itemWrapper.category)
        assertThat(lineItem.description).isEqualTo(itemWrapper.description)
        assertThat(lineItem.taxEnabled).isEqualTo(itemWrapper.taxable)
        assertThat(lineItem.amount).isEqualTo(itemWrapper.amount)
    }

    @Test
    public void testSavingLineItem() throws Exception {
        def item = HourlyLineItemWrapper.createTestingHourlyLineItem()
        lineItemConverter.saveLineItem(item)
        def retrieved = lineItemConverter.getLineItemFromId(item.id)
        assertThat(retrieved).isInstanceOf(HourlyLineItem)
        assertThat(retrieved.hours).isEqualTo(item.hours)
        assertThat(retrieved.hourlyRate).isEqualTo(item.hourlyRate)
    }
}
