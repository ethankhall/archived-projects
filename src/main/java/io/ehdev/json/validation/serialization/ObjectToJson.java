package io.ehdev.json.validation.serialization;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;

public class ObjectToJson {

    public static <T extends Serializable> String convert(T input) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        objectMapper.writeValue(sw, input);
        sw.close();
        return sw.toString();
    }
}
