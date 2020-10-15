package com.whisper.server.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonSerialize
@JsonDeserialize(builder = Person.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Person {

    @JsonProperty("username")
    private String username;

    @JsonProperty("uuid")
    private String uuid;


    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
