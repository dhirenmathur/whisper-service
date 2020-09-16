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
@JsonPropertyOrder({ "actionType"})
public class ActionMessage {

    @JsonProperty("actionType")
    private ActionType actionType;

    @JsonProperty(("target"))
    private List<String> target;

    @JsonProperty("initiator")
    private  String initiator;

    @JsonProperty("roomId")
    private  String roomId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
