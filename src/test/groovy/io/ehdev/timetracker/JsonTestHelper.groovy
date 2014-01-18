package io.ehdev.timetracker
import com.fasterxml.jackson.databind.ObjectMapper

class JsonTestHelper {

    static private ObjectMapper mapper = new ObjectMapper()

    static String objectToString(object) {
        def sw = new StringWriter()
        mapper.writeValue(sw, object)
        sw.close()
        return sw.toString()
    }

    static <T> T stringToObject(String json, Class<T> clazz){
        return mapper.readValue(json, clazz)
    }
}
