package io.ehdev.easyinvoice.interfaces
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.joda.ser.DurationSerializer
import org.joda.time.Duration

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "lineItemType")
class HourlyLineItemWrapper extends LineItemWrapper{

    def hourlyRate
    @JsonSerialize(using=DurationSerializer.class)
    Duration hours

    static HourlyLineItemWrapper  createTestingHourlyLineItem(){
        def hourlyWrapper = HourlyLineItemWrapper.newInstance()
        hourlyWrapper.with{
            id = UUID.randomUUID().toString().replace("-", "")
            description = "Humm.... "
            hourlyRate = BigDecimal.valueOf(123)
            hours = Duration.standardHours(2)
            amount = BigDecimal.valueOf(246)
        }

        return hourlyWrapper
    }

    @Override
    public String toString() {
        return "HourlyLineItemWrapper{" +
                "hourlyRate=" + hourlyRate +
                ", hours=" + hours.getMillis() +
                ", super=" + super.toString() +
                '}';
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof HourlyLineItemWrapper)) return false
        if (!super.equals(o)) return false

        HourlyLineItemWrapper that = (HourlyLineItemWrapper) o

        if (hourlyRate != that.hourlyRate) return false
        if (hours != that.hours) return false

        return true
    }

    int hashCode() {
        int result
        result = (hourlyRate != null ? hourlyRate.hashCode() : 0)
        result = 31 * result + (hours != null ? hours.hashCode() : 0)
        return result
    }
}
