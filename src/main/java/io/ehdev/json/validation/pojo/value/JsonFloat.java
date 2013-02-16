package io.ehdev.json.validation.pojo.value;

import io.ehdev.json.validation.pojo.validation.JsonValidationElement;

public class JsonFloat implements JsonEntry {

    private String value;

    public JsonFloat(Float input){
        value = input.toString();
    }

    public JsonFloat(Double input){
        value = input.toString();
    }

    @Override
    public boolean isValid(JsonValidationElement entry) {
        return entry.isEntryValid(value);
    }
}
