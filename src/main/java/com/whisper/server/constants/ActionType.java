package com.whisper.server.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {
    connect("connect"),
    online("online"),
    disconnect("disconnect"),
    start("start"),
    whisper("whisper"),
    accept("accept"),
    reject("reject");


    private final String name;

    ActionType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

}
