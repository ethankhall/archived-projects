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
