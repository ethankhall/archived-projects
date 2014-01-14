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
