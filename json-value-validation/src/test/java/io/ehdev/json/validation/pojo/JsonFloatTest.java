package io.ehdev.json.validation.pojo;


import io.ehdev.json.validation.exception.ValueOutOfBounds;
import io.ehdev.json.validation.pojo.validation.JsonValidationElement;
import io.ehdev.json.validation.pojo.validation.JsonValidationFloat;
import io.ehdev.json.validation.serialization.ObjectToJson;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class JsonFloatTest {
    @Test
    public void testSetLowValue() throws Exception {
        JsonValidationElement value = new JsonValidationFloat().setLowValue(10.0f);
        assertTrue(value.isEntryValid("12.0"));
        assertFalse(value.isEntryValid("9.0"));
    }

    @Test
    public void testSetHighValue() throws Exception {
        JsonValidationElement value = new JsonValidationFloat().setHighValue(10f);
        assertTrue(value.isEntryValid("9.0"));
        assertFalse(value.isEntryValid("11.0"));
    }

    @Test
    public void testSetHighLowValue() throws Exception {
        JsonValidationElement value = new JsonValidationFloat().setHighValue(10.0f).setLowValue(5f).isInclusive(true);
        assertTrue(value.isEntryValid("9.0"));
        assertFalse(value.isEntryValid("11"));
        assertFalse(value.isEntryValid("4"));
    }

    @Test(expected = ValueOutOfBounds.class)
    public void testOutOfBoundsException(){
        new JsonValidationFloat().setHighValue(10f).setLowValue(11f);
    }

    @Test(expected = ValueOutOfBounds.class)
    public void testOutOfBoundsException2(){
        new JsonValidationFloat().setLowValue(11f).setHighValue(10f);
    }

    @Test
    public void testIsEntryValid() throws Exception {
        JsonValidationElement value = new JsonValidationFloat();
        assertFalse(value.isEntryValid("text"));
        assertTrue(value.isEntryValid("12"));
        assertTrue(value.isEntryValid("12.0"));

    }

    @Test
    public void testNull() throws Exception {
        assertTrue(new JsonValidationFloat().setNullAvailable(true).isEntryValid("null"));
        assertTrue(new JsonValidationFloat().setNullAvailable(true).isEntryValid(null));
    }
}
