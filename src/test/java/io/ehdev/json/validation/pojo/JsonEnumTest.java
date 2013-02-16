package io.ehdev.json.validation.pojo;

import io.ehdev.json.validation.pojo.validation.JsonValidationElement;
import io.ehdev.json.validation.pojo.validation.JsonValidationEnum;
import io.ehdev.json.validation.serialization.ObjectToJson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonEnumTest {
    @Test
    public void testAddValueList() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("value1");
        JsonValidationElement element = new JsonValidationEnum().addValue(list);
        assertTrue(element.isEntryValid("value1"));
        assertFalse(element.isEntryValid("value"));
        assertFalse(element.isEntryValid("VALUE1"));
    }

    @Test
    public void testAddValueString() throws Exception {
        JsonValidationElement element = new JsonValidationEnum().addValue("value1");
        assertTrue(element.isEntryValid("value1"));
        assertFalse(element.isEntryValid("value"));
    }

    @Test
    public void testSetCaseSensitive() throws Exception {
        JsonValidationElement element = new JsonValidationEnum().addValue("value1").setCaseSensitive(false);
        assertTrue(element.isEntryValid("VALUE1"));
        assertFalse(element.isEntryValid("value"));
    }

    @Test
    public void testNull() throws Exception {
        assertTrue(new JsonValidationEnum().setNullAvailable(true).isEntryValid("null"));
        assertTrue(new JsonValidationEnum().setNullAvailable(true).isEntryValid(null));
    }
}
