package com.whisper.server.constants;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActionType {
    connect("connect"),
    disconnect("disconnect"),
    start("start"),
    whisper("whisper"),
    accept("accept"),
    decline("decline");

     //todo: add mute

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
