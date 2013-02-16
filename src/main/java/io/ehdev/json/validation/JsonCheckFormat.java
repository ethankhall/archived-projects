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
