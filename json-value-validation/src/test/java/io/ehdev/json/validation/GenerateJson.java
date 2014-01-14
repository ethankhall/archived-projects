package io.ehdev.json.validation;

import io.ehdev.json.validation.pojo.validation.JsonValidationBoolean;
import io.ehdev.json.validation.pojo.validation.JsonValidationEnum;
import io.ehdev.json.validation.pojo.validation.JsonValidationFloat;
import io.ehdev.json.validation.pojo.validation.JsonValidationInteger;
import io.ehdev.json.validation.serialization.ObjectToJson;
import org.junit.Test;

import java.io.IOException;

public class GenerateJson {

    @Test
    public void test() throws IOException {
        System.out.println(ObjectToJson.convert(
                new JsonValidationInteger().setHighValue(10).setLowValue(5).isInclusive(true)
        ));

        System.out.println(ObjectToJson.convert(
                new JsonValidationFloat().setHighValue(10.0f).setLowValue(5f).isInclusive(true)
        ));

        System.out.println(ObjectToJson.convert(
                new JsonValidationEnum().addValue("value1").setCaseSensitive(false)
        ));

        System.out.println(ObjectToJson.convert(
                new JsonValidationBoolean()
        ));
    }
}
