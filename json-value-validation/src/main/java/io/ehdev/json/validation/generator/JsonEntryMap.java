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

package io.ehdev.json.validation.generator;

import io.ehdev.json.validation.exception.JsonParseError;
import io.ehdev.json.validation.pojo.value.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import java.io.IOException;
import java.util.*;

public class JsonEntryMap {

    private ObjectMapper om = new ObjectMapper();
    private HashMap<String, JsonEntry> mapValues = new HashMap<String, JsonEntry>();

    public JsonEntryMap(String inputJson) throws IOException {
        JsonNode jsonNode = getJsonTreeFromString(inputJson);
        getValues(jsonNode, "");
    }

    private void getValues(JsonNode node, String fullName ) throws IOException {
        Iterator<Map.Entry<String, JsonNode>> iterateMap = node.getFields();
        while ( iterateMap.hasNext() ){
            Map.Entry<String, JsonNode> entry = iterateMap.next();

            String fullPath = fullName + entry.getKey();

            switch(entry.getValue().asToken()){
                case START_OBJECT:
                    getValues(entry.getValue(), fullName + entry.getKey() + ".");
                    break;
                case START_ARRAY:
                    mapValues.put(fullPath, new JsonArray(getArray(entry.getValue())));
                    break;
                case VALUE_STRING:
                    mapValues.put(fullPath, new JsonArray(entry.getValue().getTextValue()));
                    break;
                case VALUE_NUMBER_FLOAT:
                    mapValues.put(fullPath, new JsonFloat(entry.getValue().getDoubleValue()));
                    break;
                case VALUE_NUMBER_INT:
                    mapValues.put(fullPath, new JsonInteger(entry.getValue().getIntValue()));
                    break;
                case VALUE_FALSE:
                case VALUE_TRUE:
                    mapValues.put(fullPath, new JsonBoolean(entry.getValue().getBooleanValue()));
                    break;
                case VALUE_NULL:
                    mapValues.put(fullPath, new JsonNull());
                    break;
                default:
                    System.out.println(entry.getValue().asToken());
                    break;
            }
        }
    }

    private List<String> getArray(JsonNode node){
        ArrayNode input = (ArrayNode)node;
        List<String> returnValue = new ArrayList<String>();

        for(int i = 0; i < input.size(); i++){
            JsonNode node1 = input.get(i);
            returnValue.add(node1.asText());

        }

        return returnValue;
    }

    private JsonNode getJsonTreeFromString(String inputJson) {
        try{
            return om.readTree(inputJson);
        } catch (Exception e){
            throw new JsonParseError();
        }
    }

    public HashMap<String, JsonEntry> getFullPathWithValues(){
        return new HashMap<String, JsonEntry>(mapValues);
    }


}
