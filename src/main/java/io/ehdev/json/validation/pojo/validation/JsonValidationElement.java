package io.ehdev.json.validation.pojo.validation;


import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonAutoDetect()
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonValidationBoolean.class),
        @JsonSubTypes.Type(value = JsonValidationEnum.class),
        @JsonSubTypes.Type(value = JsonValidationFloat.class),
        @JsonSubTypes.Type(value = JsonValidationInteger.class),
})

public abstract class JsonValidationElement implements Serializable {

    abstract public boolean isEntryValid(String value);
    abstract public boolean isNullAcceptable();
}
