package io.ehdev.timetracker.services.external.company

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonBuilder

class ExternalCompanyBuilder {

    public static ExternalCompany createCompany(String json){
        ObjectMapper om = new ObjectMapper()
        return om.readValue(json, ExternalCompany.class)
    }

    public static ExternalCompany createCompany(Map json){
        return createCompany(new JsonBuilder(json).toString())
    }
}
