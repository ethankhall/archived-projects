package io.ehdev.easyinvoice.invoice
import io.ehdev.easyinvoice.lineitem.LineItemImpl
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

import static org.fest.assertions.Assertions.assertThat

class InvoiceImplTest {

    Invoice invoice;

    @BeforeMethod
    public void setup(){
        invoice = new InvoiceImpl()
    }

    @Test
    public void testAddingLineItem() throws Exception {
        invoice.addLineItem(new LineItemImpl(BigDecimal.ONE))
        assertThat(invoice.lineItems).hasSize(1)
    }

    @Test
    public void testAbleToFindByType() throws Exception {
        def item1 = new LineItemImpl(BigDecimal.ONE, "cat1")
        def item2 = new LineItemImpl(BigDecimal.ONE, "cat2")
        invoice.addLineItem(item1)
        invoice.addLineItem(item2)
        def foundCategory = invoice.getLineItemsForCategory("cat1")
        assertThat(foundCategory).hasSize(1)
        assertThat(foundCategory[0].category).isEqualTo(item1.category)
    }

    @Test
    public void testFindingNoByType() throws Exception {
        assertThat(invoice.getLineItemsForCategory("")).hasSize(0)
    }

    @Test
    public void testFindItemWithoutCategory() throws Exception {
        invoice.addLineItem(new LineItemImpl(BigDecimal.ONE))
        assertThat(invoice.getLineItemsForCategory("")).hasSize(1)
    }

    @Test
    public void testGettingCategoryItems() throws Exception {
        invoice.addLineItems(
                [
                        new LineItemImpl(BigDecimal.ONE),
                        new LineItemImpl(BigDecimal.TEN,  "1"),
                        new LineItemImpl(BigDecimal.ZERO, "2")
                ] )
        def categories = invoice.getCategories()
        assertThat(categories).hasSize(3)
        assertThat(categories).contains("")
        assertThat(categories).contains("1")
        assertThat(categories).contains("2")
    }

    @Test
    public void testFindItemById() throws Exception {
        def item = new LineItemImpl(BigDecimal.TEN)
        invoice.addLineItem(item)
        invoice.getRemoveLineItem(item.getId());
        assertThat(invoice.getLineItems()).hasSize(0);
    }

    @Test
    public void testConfigureTaxabilityForCategory() throws Exception {
        invoice.addLineItems(
                [
                        new LineItemImpl(BigDecimal.ONE),
                        new LineItemImpl(BigDecimal.TEN,  "1"),
                        new LineItemImpl(BigDecimal.ZERO, "2")
                ] )
        invoice.disableTaxForCategory("1")
        invoice.setTaxRate(10)
        assertThat(invoice.getAmount()).isEqualTo(BigDecimal.valueOf(10 + 1.10).setScale(3))

        invoice.enableTaxForCategory("1")
        invoice.setTaxRate(20)
        assertThat(invoice.getAmount()).isEqualTo(BigDecimal.valueOf(11 * 1.20).setScale(3))
    }
}
