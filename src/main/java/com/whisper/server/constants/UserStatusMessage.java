package com.whisper.server.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonSerialize
@JsonDeserialize(builder = ActionMessage.Builder.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserStatusMessage {

    @JsonProperty("userList")
    private Map<String,String> userList;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}
