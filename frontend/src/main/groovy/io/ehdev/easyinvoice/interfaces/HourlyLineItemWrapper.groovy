package io.ehdev.easyinvoice.interfaces
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.joda.ser.DurationSerializer
import org.joda.time.Duration

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "lineItemType")
class HourlyLineItemWrapper extends LineItemWrapper{

    def rate
    @JsonSerialize(using=DurationSerializer.class)
    Duration time

    static HourlyLineItemWrapper  createTestingHourlyLineItem(){
        def hourlyWrapper = HourlyLineItemWrapper.newInstance()
        hourlyWrapper.with{
            id = UUID.randomUUID().toString().replace("-", "")
            description = "Humm.... "
            rate = BigDecimal.valueOf(123)
            time = Duration.standardHours(2)
            amount = BigDecimal.valueOf(246)
        }

        return hourlyWrapper
    }

    @Override
    public String toString() {
        return "HourlyLineItemWrapper{" +
                "rate=" + rate +
                ", time=" + time.getMillis() +
                ", super=" + super.toString() +
                '}';
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof HourlyLineItemWrapper)) return false
        if (!super.equals(o)) return false

        HourlyLineItemWrapper that = (HourlyLineItemWrapper) o

        if (rate != that.rate) return false
        if (time != that.time) return false

        return true
    }

    int hashCode() {
        int result
        result = (rate != null ? rate.hashCode() : 0)
        result = 31 * result + (time != null ? time.hashCode() : 0)
        return result
    }
}
