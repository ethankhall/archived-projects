package io.ehdev.json.validation.pojo.value;

import io.ehdev.json.validation.pojo.validation.JsonValidationElement;

public class JsonNull implements JsonEntry {

    @Override
    public boolean isValid(JsonValidationElement entry) {
        return entry.isNullAcceptable();
    }
}
