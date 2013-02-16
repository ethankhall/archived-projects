package io.ehdev.json.validation.pojo.validation;

import io.ehdev.json.validation.exception.ValueOutOfBounds;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

public class JsonValidationFloat extends JsonValidationElement {

    @JsonProperty
    private Float lowValue = null;
    @JsonProperty
    private Float highValue = null;
    @JsonProperty
    private boolean isInclusive = false;
    @JsonProperty
    private boolean nullAcceptable = false;

    public JsonValidationFloat(){}

    public JsonValidationFloat setNullAvailable(boolean value){
        nullAcceptable = value;
        return this;
    }

    public JsonValidationFloat isInclusive(boolean value){
        isInclusive = value;
        return this;
    }

    public JsonValidationFloat setLowValue(Float value){

        if(null != highValue && highValue < value) throw new ValueOutOfBounds();
        lowValue = value;
        return this;
    }

    public JsonValidationFloat setHighValue(Float value){
        if(null != lowValue && lowValue > value) throw new ValueOutOfBounds();
        highValue = value;
        return this;
    }

    @Override
    public boolean isEntryValid(String inputValue) {

        if(nullAcceptable && null == inputValue || StringUtils.equalsIgnoreCase("null", inputValue) ) return true;

        try{
            Float value = Float.parseFloat(inputValue);
            return isEntryValid(value);
        } catch (Exception e) {
            return false;
        }

    }

    private boolean isEntryValid(Float value) {
        if(null != lowValue && !isInclusive && lowValue >= value) return false;
        if(null != highValue && !isInclusive  && highValue <= value) return false;

        //Inclusive test
        if(null != lowValue && lowValue >= value) return false;
        if(null != highValue && highValue <= value) return false;

        return true;
    }

    @Override
    public boolean isNullAcceptable() {
        return nullAcceptable;
    }
}
