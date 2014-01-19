package io.ehdev.timetracker.services.external
import com.fasterxml.jackson.annotation.JsonProperty
import io.ehdev.timetracker.core.entry.LineItemEntry
import io.ehdev.timetracker.core.project.discount.Discount
import io.ehdev.timetracker.core.project.rate.Rate

class ExternalProject {

    @JsonProperty(required = true)
    String name

    String uuid
    Rate rate
    Discount discount
    List<LineItemEntry> lineItems

    List<String> readPermissions
    List<String> writePermissions

    String companyUuid

}
