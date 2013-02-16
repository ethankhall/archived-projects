package io.ehdev.json.validation.pojo.validation;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class JsonValidationEnum extends JsonValidationElement {

    @JsonProperty
    private List<String> enumValues = new ArrayList<String>();
    @JsonProperty
    private boolean caseSensitive = true;
    @JsonProperty
    private boolean nullAcceptable = false;

    public JsonValidationEnum(){}

    public JsonValidationEnum setNullAvailable(boolean value){
        nullAcceptable = value;
        return this;
    }

    public JsonValidationEnum addValue(List<String> enums){
        enumValues.addAll(enums);
        return this;
    }
    public JsonValidationEnum addValue(String enumValue){
        enumValues.add(enumValue);
        return this;
    }

    public JsonValidationEnum setCaseSensitive(boolean caseSensitive){
        this.caseSensitive = caseSensitive;
        return this;
    }

    @Override
    public boolean isEntryValid(String inputValue) {

        if(nullAcceptable && null == inputValue || StringUtils.equalsIgnoreCase("null", inputValue) ) return true;

        return (caseSensitive) ? checkCaseSensitive(inputValue) : checkNonCaseSensitive(inputValue);
    }

    private boolean checkNonCaseSensitive(String inputValue) {
        for(String value: enumValues){
            if(StringUtils.equalsIgnoreCase(value, inputValue)) return true;
        }

        return false;
    }

    private boolean checkCaseSensitive(String inputValue) {
        for(String value: enumValues){
            if(StringUtils.equals(value, inputValue)) return true;
        }

        return false;
    }

    @Override
    public boolean isNullAcceptable() {
        return nullAcceptable;
    }
}
