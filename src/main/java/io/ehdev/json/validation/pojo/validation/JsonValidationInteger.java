package io.ehdev.json.validation.pojo.validation;

import io.ehdev.json.validation.exception.ValueOutOfBounds;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

public class JsonValidationInteger extends JsonValidationElement {

    @JsonProperty
    private Integer lowValue = null;
    @JsonProperty
    private Integer highValue = null;
    @JsonProperty
    private boolean isInclusive = false;
    @JsonProperty
    private boolean nullAcceptable = false;


    public JsonValidationInteger(){}

    public JsonValidationInteger setNullAvailable(boolean value){
        nullAcceptable = value;
        return this;
    }

    public JsonValidationInteger isInclusive(boolean value){
        isInclusive = value;
        return this;
    }

    public JsonValidationInteger setLowValue(Integer value){

        if(null != highValue && highValue < value) throw new ValueOutOfBounds();
        lowValue = value;
        return this;
    }

    public JsonValidationInteger setHighValue(Integer value){
        if(null != lowValue && lowValue > value) throw new ValueOutOfBounds();
        highValue = value;
        return this;
    }

    @Override
    public boolean isEntryValid(String inputValue) {

        if(nullAcceptable && null == inputValue || StringUtils.equalsIgnoreCase("null", inputValue) ) return true;

        try{
            Integer value = Integer.parseInt(inputValue);
            return isEntryValid(value);
        } catch (Exception e) {
            return false;
        }

    }

    private boolean isEntryValid(Integer value) {
        if(null != lowValue && !isInclusive && lowValue >= value) return false;
        if(null != highValue && !isInclusive  && highValue <= value) return false;

        //Inclusive test
        if(null != lowValue && lowValue > value) return false;
        if(null != highValue && highValue < value) return false;

        return true;
    }

    @Override
    public boolean isNullAcceptable() {
        return nullAcceptable;
    }
}
