/*******************************************************************************
 * Copyright (c) 2013 Ethan Hall
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

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
