package io.ehdev.json.validation.pojo.validation;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

public class JsonValidationBoolean extends JsonValidationElement {

    @JsonProperty
    private boolean nullAcceptable = false;

    public JsonValidationBoolean(){}

    public JsonValidationBoolean setNullAvailable(boolean value){
        nullAcceptable = value;
        return this;
    }

    @Override
    public boolean isEntryValid(String inputValue) {
        if(nullAcceptable && null == inputValue)
            return true;

        if( StringUtils.equalsIgnoreCase("null", inputValue) ) return true;
        if( StringUtils.isBlank(inputValue)) return false;

        try{
            Boolean.parseBoolean(inputValue);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public boolean isNullAcceptable() {
        return nullAcceptable;
    }

}
