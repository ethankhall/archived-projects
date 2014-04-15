package io.ehdev.easyinvoice.interfaces

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer
import org.joda.time.DateTime

public class InvoiceWrapper {
    def id
    def baseUrl
    def refreshUrl
    def lineItems = []
    def customer
    def merchant
    def tax
    def discountPercent
    def discountValue
    def subTotal
    def amountDue
    def currency = [ name: "USD", symbol: "\$" ]
    InvoiceState state = InvoiceState.IN_PROGRESS
    @JsonSerialize(using=DateTimeSerializer.class)
    DateTime lastUpdate
    @JsonSerialize(using=DateTimeSerializer.class)
    DateTime sentTime
    @JsonSerialize(using=DateTimeSerializer.class)
    DateTime paidTime

    static InvoiceWrapper createSampleInvoice(connectionString){
        def url = connectionString
        def id = UUID.randomUUID().toString().replace("-", "")
        def invoiceDetails = [id: id, baseUrl: url, refreshUrl: url + "/invoice/" + id,
                lineItems: [123, 456, 789], customer: "customerId", merchant: "merchantId",
                tax: "15%", discountValue: BigDecimal.valueOf(100), subTotal: BigDecimal.valueOf(123),
                amountDue: BigDecimal.valueOf(23),  lastUpdate: DateTime.now()]
        return new InvoiceWrapper(invoiceDetails)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        InvoiceWrapper invoice = (InvoiceWrapper) o

        if (amountDue != invoice.amountDue) return false
        if (baseUrl != invoice.baseUrl) return false
        if (currency != invoice.currency) return false
        if (customer != invoice.customer) return false
        if (discountPercent != invoice.discountPercent) return false
        if (discountValue != invoice.discountValue) return false
        if (id != invoice.id) return false
        if (lastUpdate != invoice.lastUpdate) return false
        if (lineItems != invoice.lineItems) return false
        if (merchant != invoice.merchant) return false
        if (paidTime != invoice.paidTime) return false
        if (refreshUrl != invoice.refreshUrl) return false
        if (sentTime != invoice.sentTime) return false
        if (state != invoice.state) return false
        if (subTotal != invoice.subTotal) return false
        if (tax != invoice.tax) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (baseUrl != null ? baseUrl.hashCode() : 0)
        result = 31 * result + (refreshUrl != null ? refreshUrl.hashCode() : 0)
        result = 31 * result + (lineItems != null ? lineItems.hashCode() : 0)
        result = 31 * result + (customer != null ? customer.hashCode() : 0)
        result = 31 * result + (merchant != null ? merchant.hashCode() : 0)
        result = 31 * result + (tax != null ? tax.hashCode() : 0)
        result = 31 * result + (discountPercent != null ? discountPercent.hashCode() : 0)
        result = 31 * result + (discountValue != null ? discountValue.hashCode() : 0)
        result = 31 * result + (subTotal != null ? subTotal.hashCode() : 0)
        result = 31 * result + (amountDue != null ? amountDue.hashCode() : 0)
        result = 31 * result + (currency != null ? currency.hashCode() : 0)
        result = 31 * result + (state != null ? state.hashCode() : 0)
        result = 31 * result + (lastUpdate != null ? lastUpdate.hashCode() : 0)
        result = 31 * result + (sentTime != null ? sentTime.hashCode() : 0)
        result = 31 * result + (paidTime != null ? paidTime.hashCode() : 0)
        return result
    }


    @Override
    public java.lang.String toString() {
        return "InvoiceWrapper{" +
                "id=" + id +
                ", baseUrl=" + baseUrl +
                ", refreshUrl=" + refreshUrl +
                ", lineItems=" + lineItems +
                ", customer=" + customer +
                ", merchant=" + merchant +
                ", tax=" + tax +
                ", discountPercent=" + discountPercent +
                ", discountValue=" + discountValue +
                ", subTotal=" + subTotal +
                ", amountDue=" + amountDue +
                ", currency=" + currency +
                ", state=" + state +
                ", lastUpdate=" + lastUpdate +
                ", sentTime=" + sentTime +
                ", paidTime=" + paidTime +
                '}';
    }
}
