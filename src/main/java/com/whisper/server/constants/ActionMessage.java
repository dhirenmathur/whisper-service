package com.whisper.server.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.List;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonSerialize
@JsonDeserialize(builder = ActionMessage.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ActionMessage {

    @JsonProperty("actionType")
    private ActionType actionType;

    @JsonProperty("destination")
    private List<String> destination;

    @JsonProperty("user")
    private Person user;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
