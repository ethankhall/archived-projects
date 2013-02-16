package io.ehdev.json.validation;

import io.ehdev.json.validation.generator.JsonEntryMap;
import io.ehdev.json.validation.generator.JsonValidationMap;
import io.ehdev.json.validation.pojo.validation.JsonValidationElement;
import io.ehdev.json.validation.pojo.value.JsonEntry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonCheckFormat {

    private String jsonToBeValidated;
    private String formatString;

    JsonCheckFormat(String jsonToBeValidated, String formatString){
        this.jsonToBeValidated = jsonToBeValidated;
        this.formatString = formatString;
    }

    public Boolean isJsonValid() throws IOException {
        JsonValidationMap hashMapOfJsonToBeValidated = new JsonValidationMap(jsonToBeValidated);
        JsonEntryMap jsonEntryMap = new JsonEntryMap(formatString);
        HashMap<String, JsonValidationElement> validation = hashMapOfJsonToBeValidated.getFullPathWithValidator();
        HashMap<String, JsonEntry> realValues = jsonEntryMap.getFullPathWithValues();

        for(Map.Entry<String, JsonValidationElement> entry : validation.entrySet()){
            JsonEntry jsonEntry;
            if((jsonEntry = realValues.get(entry.getKey())) != null){
                if(!jsonEntry.isValid(entry.getValue())) return false;
            } else {
                return false;
            }
        }

        return true;
    }
}
