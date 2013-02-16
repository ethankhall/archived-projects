package io.ehdev.json.validation.pojo.value;

import io.ehdev.json.validation.pojo.validation.JsonValidationElement;

public interface JsonEntry {
    public boolean isValid(JsonValidationElement entry);
}
