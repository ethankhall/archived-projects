package io.ehdev.easyinvoice.interfaces
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "lineItemType")
@JsonSubTypes([
    @JsonSubTypes.Type(value = HourlyLineItemWrapper.class, name = "hourly"),
    @JsonSubTypes.Type(value = FlatLineItemWrapper.class, name= "flat")
])
abstract class LineItemWrapper {

    def amount
    def description
    def category = ""
    def taxable = true
    String id

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof LineItemWrapper)) return false

        LineItemWrapper that = (LineItemWrapper) o

        if (amount != that.amount) return false
        if (category != that.category) return false
        if (description != that.description) return false
        if (id != that.id) return false
        if (taxable != that.taxable) return false

        return true
    }

    int hashCode() {
        int result
        result = (amount != null ? amount.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (category != null ? category.hashCode() : 0)
        result = 31 * result + (taxable != null ? taxable.hashCode() : 0)
        result = 31 * result + (id != null ? id.hashCode() : 0)
        return result
    }


    @Override
    public java.lang.String toString() {
        return "LineItemWrapper{" +
                "amount=" + amount +
                ", description=" + description +
                ", category=" + category +
                ", taxable=" + taxable +
                ", id=" + id +
                '}';
    }
}
