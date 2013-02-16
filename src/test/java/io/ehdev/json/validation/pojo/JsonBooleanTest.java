package io.ehdev.json.validation.pojo;

import io.ehdev.json.validation.pojo.validation.JsonValidationBoolean;
import io.ehdev.json.validation.serialization.ObjectToJson;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonBooleanTest {
    @Test
    public void testIsEntryValid() throws Exception {
        assertTrue(new JsonValidationBoolean().isEntryValid("true"));
        assertTrue(new JsonValidationBoolean().isEntryValid("TRUE"));
        assertTrue(new JsonValidationBoolean().isEntryValid("false"));
        assertTrue(new JsonValidationBoolean().isEntryValid("FALSE"));

        assertFalse(new JsonValidationBoolean().isEntryValid(""));
    }

    @Test
    public void testNull() throws Exception {
        assertTrue(new JsonValidationBoolean().setNullAvailable(true).isEntryValid("null"));
        assertTrue(new JsonValidationBoolean().setNullAvailable(true).isEntryValid(null));
    }
}
