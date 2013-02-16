package io.ehdev.json.validation.pojo.value;

import io.ehdev.json.validation.pojo.validation.JsonValidationElement;

public class JsonInteger implements JsonEntry {

    private String value;

    public JsonInteger(Integer input){
        value = input.toString();
    }

    @Override
    public boolean isValid(JsonValidationElement entry) {
        return entry.isEntryValid(value);
    }
}
