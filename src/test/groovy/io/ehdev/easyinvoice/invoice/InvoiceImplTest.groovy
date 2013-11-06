package io.ehdev.easyinvoice.invoice
import io.ehdev.easyinvoice.lineitem.FlatLineItem
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
        invoice.addLineItem(new FlatLineItem(BigDecimal.ONE))
        assertThat(invoice.lineItems).hasSize(1)
    }

    @Test
    public void testAbleToFindByType() throws Exception {
        def item1 = new FlatLineItem(BigDecimal.ONE, "cat1")
        def item2 = new FlatLineItem(BigDecimal.ONE, "cat2")
        invoice.addLineItem(item1)
        invoice.addLineItem(item2)
        def foundCategory = invoice.findLineItemsForCategory("cat1")
        assertThat(foundCategory).hasSize(1)
        assertThat(foundCategory[0].category).isEqualTo(item1.category)
    }

    @Test
    public void testFindingNoByType() throws Exception {
        assertThat(invoice.findLineItemsForCategory("")).hasSize(0)
    }

    @Test
    public void testFindItemWithoutCategory() throws Exception {
        invoice.addLineItem(new FlatLineItem(BigDecimal.ONE))
        assertThat(invoice.findLineItemsForCategory("")).hasSize(1)
    }

    @Test
    public void testGettingCategoryItems() throws Exception {
        invoice.addLineItems(
                [
                        new FlatLineItem(BigDecimal.ONE),
                        new FlatLineItem(BigDecimal.TEN,  "1"),
                        new FlatLineItem(BigDecimal.ZERO, "2")
                ] )
        def categories = invoice.getCategories()
        assertThat(categories).hasSize(3)
        assertThat(categories).contains("")
        assertThat(categories).contains("1")
        assertThat(categories).contains("2")
    }

    @Test
    public void testFindItemById() throws Exception {
        def item = new FlatLineItem(BigDecimal.TEN)
        invoice.addLineItem(item)
        invoice.getRemoveLineItem(item.getId());
        assertThat(invoice.getLineItems()).hasSize(0);
    }

    @Test
    public void testConfigureTaxabilityForCategory() throws Exception {
        invoice.addLineItems(
                [
                        new FlatLineItem(BigDecimal.ONE),
                        new FlatLineItem(BigDecimal.TEN,  "1"),
                        new FlatLineItem(BigDecimal.ZERO, "2")
                ] )
        invoice.disableTaxForCategory("1")
        invoice.setTaxRate(10)
        assertThat(invoice.calculateAmount()).isEqualTo(BigDecimal.valueOf(11).setScale(3))
        assertThat(invoice.calculateTaxDue()).isEqualTo(BigDecimal.valueOf(0.1).setScale(3))

        invoice.enableTaxForCategory("1")
        invoice.setTaxRate(20)
        assertThat(invoice.calculateAmount()).isEqualTo(BigDecimal.valueOf(11).setScale(3))
        assertThat(invoice.calculateTaxDue()).isEqualTo(BigDecimal.valueOf(11 * 0.2).setScale(3))
    }
}
