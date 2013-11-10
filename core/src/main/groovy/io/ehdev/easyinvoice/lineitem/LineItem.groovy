package io.ehdev.easyinvoice.lineitem

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer
import org.joda.time.DateTime

abstract class LineItem {

    def description = ""
    def category = ""

    @JsonSerialize(using=DateTimeSerializer.class)
    def dateFinished = DateTime.now()
    def taxEnabled = true
    def id = UUID.randomUUID() as String
}
