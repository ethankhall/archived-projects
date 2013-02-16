package io.ehdev.json.validation;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonCheckFormatTest {

    @Test
    public void testIsJsonValid() throws Exception {
        File validationFile = FileUtils.toFile( this.getClass().getResource("/validation.json"));
        String validationJson = FileUtils.readFileToString(validationFile);
        File valueFile = FileUtils.toFile( this.getClass().getResource("/test.json"));
        String valueJson = FileUtils.readFileToString(valueFile);
        JsonCheckFormat jcf = new JsonCheckFormat(validationJson, valueJson);
        assertTrue(jcf.isJsonValid());
    }

    @Test
    public void testOutOfRange() throws Exception {
        File validationFile = FileUtils.toFile( this.getClass().getResource("/validation.json"));
        String validationJson = FileUtils.readFileToString(validationFile);
        File valueFile = FileUtils.toFile( this.getClass().getResource("/test_out_of_range.json"));
        String valueJson = FileUtils.readFileToString(valueFile);
        JsonCheckFormat jcf = new JsonCheckFormat(validationJson, valueJson);
        assertFalse(jcf.isJsonValid());
    }

    @Test
    public void testEnum() throws Exception {
        File validationFile = FileUtils.toFile( this.getClass().getResource("/validation_enum.json"));
        String validationJson = FileUtils.readFileToString(validationFile);
        File valueFile = FileUtils.toFile( this.getClass().getResource("/test.json"));
        String valueJson = FileUtils.readFileToString(valueFile);
        JsonCheckFormat jcf = new JsonCheckFormat(validationJson, valueJson);
        assertTrue(jcf.isJsonValid());
    }
}
