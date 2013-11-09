package io.ehdev.easyinvoice.lineitem

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName

@JsonTypeName("hourly")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "lineItemType")
class HourlyLineItem extends LineItem{

    BigDecimal hourlyRate
    Double hours

}
