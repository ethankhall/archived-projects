package io.ehdev.easyinvoice.lineitem
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="lineItemType")
@JsonAutoDetect(
        fieldVisibility=JsonAutoDetect.Visibility.ANY,
        getterVisibility=JsonAutoDetect.Visibility.NONE,
        isGetterVisibility=JsonAutoDetect.Visibility.NONE)
class HourlyLineItem extends LineItem{

    BigDecimal hourlyRate
    Double hours

    public HourlyLineItem(BigDecimal hourlyRate, double hours) {
        this(hourlyRate, hours, "")
    }

    public HourlyLineItem(BigDecimal hourlyRate, double hours, def category) {
        super(category)
        this.hourlyRate = hourlyRate
        this.hours = hours
    }

}
