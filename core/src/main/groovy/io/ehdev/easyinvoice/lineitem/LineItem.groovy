package io.ehdev.easyinvoice.lineitem

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer
import org.joda.time.DateTime

@JsonSubTypes([ @JsonSubTypes.Type(value = HourlyLineItem.class), @JsonSubTypes.Type(value = FlatLineItem.class)])
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "lineItemType")
abstract class LineItem {

    def description = ""
    def category = ""

    @JsonSerialize(using=DateTimeSerializer.class)
    def dateFinished = DateTime.now()
    def taxEnabled = true
    final def id = UUID.randomUUID() as String
}
