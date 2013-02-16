package io.ehdev.json.validation.generator;

import io.ehdev.json.validation.exception.JsonParseError;
import io.ehdev.json.validation.exception.JsonValidationElementNotFoundException;
import io.ehdev.json.validation.pojo.validation.JsonValidationElement;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class JsonValidationMap {

    private ObjectMapper om = new ObjectMapper();
    private HashMap<String, JsonValidationElement> mapValues = new HashMap<String, JsonValidationElement>();

    public JsonValidationMap(String inputJson) throws IOException {
        JsonNode jsonNode = getJsonTreeFromString(inputJson);
        getValues(jsonNode, "");
    }

    private void getValues(JsonNode node, String fullName ) throws IOException {
        Iterator<Map.Entry<String, JsonNode>> iterateMap = node.getFields();
        while ( iterateMap.hasNext() ){
            Map.Entry<String, JsonNode> entry = iterateMap.next();

            switch(entry.getValue().asToken()){
                case START_OBJECT:
                    try {
                        JsonValidationElement element = isJsonValidationElement(entry.getValue());
                        mapValues.put(fullName + entry.getKey(), element);
                    } catch (JsonValidationElementNotFoundException e) {
                        getValues(entry.getValue(), fullName + entry.getKey() + ".");
                    }
                    break;
                case START_ARRAY:
                    System.out.print(fullName + entry.getKey());
                    System.out.println("[" + printArray(entry.getValue()) + "]");
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER_FLOAT:
                case VALUE_NUMBER_INT:
                    System.out.print(fullName + entry.getKey());
                    System.out.println(" : " + entry.getValue().asText());
                    break;
                default:
                    System.out.println(entry.getValue().asToken());
                    break;
            }
        }
    }

    private JsonValidationElement isJsonValidationElement(JsonNode input) throws JsonValidationElementNotFoundException {
        try {
            return om.readValue(getStringFromJsonNode(input), JsonValidationElement.class);
        } catch (IOException e) {
            throw new JsonValidationElementNotFoundException();
        }
    }

    private String getStringFromJsonNode(JsonNode input) throws IOException {
        StringWriter sw = new StringWriter();
        om.writeValue(sw, input);
        sw.close();
        return sw.toString();

    }

    private String printArray(JsonNode node){
        ArrayNode input = (ArrayNode)node;
        Stack<String> name = new Stack<String>();

        String returnValue = "";

        for(int i = 0; i < input.size(); i++){
            JsonNode node1 = input.get(i);
            name.push(node1.asText());
        }

        while(!name.empty()){
            returnValue += name.pop();
            if(!name.empty()) returnValue += ",";
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

    public HashMap<String, JsonValidationElement> getFullPathWithValidator(){
        return new HashMap<String, JsonValidationElement>(mapValues);
    }


}
