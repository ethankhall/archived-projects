package io.ehdev.easyinvoice.interfaces
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "lineItemType")
class FlatLineItemWrapper extends LineItemWrapper {

    def amount

    static FlatLineItemWrapper  createTestingFlatLineItem(){
        def flatWrapper = FlatLineItemWrapper.newInstance()
        flatWrapper.with{
            id = UUID.randomUUID().toString().replace("-", "")
            description = "Humm.... "
            amount = BigDecimal.valueOf(246)
        }

        return flatWrapper
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof FlatLineItemWrapper)) return false
        if (!super.equals(o)) return false

        FlatLineItemWrapper that = (FlatLineItemWrapper) o

        if (amount != that.amount) return false

        return true
    }

    int hashCode() {
        int result = super.hashCode()
        result = 31 * result + (amount != null ? amount.hashCode() : 0)
        return result
    }


    @Override
    public java.lang.String toString() {
        return "FlatLineItemWrapper{" +
                "amount=" + amount +
                "super=" + super.toString() +
                '}';
    }
}
