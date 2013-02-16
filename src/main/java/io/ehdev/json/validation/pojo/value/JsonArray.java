package io.ehdev.json.validation.pojo.value;

import io.ehdev.json.validation.pojo.validation.JsonValidationElement;

import java.util.ArrayList;
import java.util.List;

public class JsonArray implements JsonEntry {

    private List<String> entries = new ArrayList<String>();

    public JsonArray() {}

    public JsonArray(String input){
        entries.add(input);
    }

    public JsonArray(List<String> input){
        entries.addAll(input);
    }



    @Override
    public boolean isValid(JsonValidationElement entry) {
        for(String testValue : entries){
            if(!entry.isEntryValid(testValue)){
                return false;
            }
        }

        return true;
    }
}
