package io.ehdev.json.validation.pojo;


import io.ehdev.json.validation.exception.ValueOutOfBounds;
import io.ehdev.json.validation.pojo.validation.JsonValidationElement;
import io.ehdev.json.validation.pojo.validation.JsonValidationInteger;
import io.ehdev.json.validation.serialization.ObjectToJson;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class JsonIntegerTest {
    @Test
    public void testSetLowValue() throws Exception {
        JsonValidationElement value = new JsonValidationInteger().setLowValue(10);
        assertTrue(value.isEntryValid("12"));
        assertFalse(value.isEntryValid("9"));
    }

    @Test
    public void testSetHighValue() throws Exception {
        JsonValidationElement value = new JsonValidationInteger().setHighValue(10);
        assertTrue(value.isEntryValid("9"));
        assertFalse(value.isEntryValid("11"));
    }

    @Test
    public void testSetHighLowValue() throws Exception {
        JsonValidationElement value = new JsonValidationInteger().setHighValue(10).setLowValue(5);
        assertTrue(value.isEntryValid("9"));
        assertFalse(value.isEntryValid("11"));
        assertFalse(value.isEntryValid("4"));
    }

    @Test(expected = ValueOutOfBounds.class)
    public void testOutOfBoundsException(){
        new JsonValidationInteger().setHighValue(10).setLowValue(11);
    }

    @Test(expected = ValueOutOfBounds.class)
    public void testOutOfBoundsException2(){
        new JsonValidationInteger().setLowValue(11).setHighValue(10);
    }

    @Test
    public void testIsEntryValid() throws Exception {
        JsonValidationElement value = new JsonValidationInteger();
        assertFalse(value.isEntryValid("text"));
        assertTrue(value.isEntryValid("12"));
    }

    @Test
    public void testValidation() throws Exception {
        JsonValidationInteger value = new JsonValidationInteger().setHighValue(10).setLowValue(5);
        assertFalse(value.isEntryValid("10"));
        value.isInclusive(true);
        assertTrue(value.isEntryValid("10"));
    }

    @Test
    public void testNull() throws Exception {
        assertTrue(new JsonValidationInteger().setNullAvailable(true).isEntryValid("null"));
        assertTrue(new JsonValidationInteger().setNullAvailable(true).isEntryValid(null));
    }
}
