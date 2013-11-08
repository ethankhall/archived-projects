package io.ehdev.easyinvoice.lineitem
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer
import org.joda.time.DateTime

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="lineItemType")
@JsonSubTypes([
    @JsonSubTypes.Type( value=HourlyLineItem.class, name = "hourly" ),
    @JsonSubTypes.Type( value=FlatLineItem.class, name = "flat" )
])
@JsonAutoDetect(
        fieldVisibility=JsonAutoDetect.Visibility.ANY,
        getterVisibility=JsonAutoDetect.Visibility.NONE,
        isGetterVisibility=JsonAutoDetect.Visibility.NONE)
abstract class LineItem {

    def description
    def category = ""
    @JsonSerialize(using=DateTimeSerializer.class)
    def dateFinished = DateTime.now()
    def taxEnabled = true
    final def id = UUID.randomUUID() as String
}
